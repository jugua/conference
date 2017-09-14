package com.epam.cm;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import org.jbehave.core.model.Scenario;
import org.jbehave.core.model.Story;
import org.jbehave.core.parsers.RegexStoryParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class StoryBatchSorter {
    private static final Logger LOG = LoggerFactory.getLogger(StoryBatchSorter.class.getSimpleName());

    /**
     * Returns list of story paths for current agent based on average number of scenarios to make minimize deviation
     * between execution times of agents.
     *
     * @param stories
     *            List of all stories in suite
     * @param agentTotal
     *            Total number of agents
     * @param agentNumber
     *            Current agent Number
     * @return List of stories for current agent
     */
    public static List<String> getStoriesForCurrentAgent(final List<String> stories, final Integer agentTotal,
            final Integer agentNumber) {
        Map<Integer, List<String>> storiesForAgentMap = new HashMap<>();
        Map<Integer, Integer> agentWeightMap = new HashMap<>();
        Map<String, Integer> storyWeightMap = generateStoryWeightMap(stories);

        LinkedHashMap<String, Integer> ascSortedStoryWeightMap = storyWeightMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        LinkedHashMap<String, Integer> descSortedStoryWeightMap = storyWeightMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.<Integer> reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        LOG.debug("Ascending sorted story weight map");
        ascSortedStoryWeightMap.forEach((k, v) -> LOG.debug("Story: " + k + " Weight: " + v));
        LOG.debug("Descending sorted story weight map");
        descSortedStoryWeightMap.forEach((k, v) -> LOG.debug("Story: " + k + " Weight: " + v));

        Integer suiteWeight = storyWeightMap.values().stream().mapToInt(Integer::intValue).sum();
        Integer averageWeight = suiteWeight / agentTotal == 0 ? 1 : suiteWeight / agentTotal;

        for (int currentAgentNumber = 1; currentAgentNumber <= agentTotal; currentAgentNumber++) {
            int currentAgentWeight = 0;
            storiesForAgentMap.put(currentAgentNumber, new ArrayList<>());

            if (currentAgentNumber == agentTotal && areAnyStoriesLeft(descSortedStoryWeightMap)) {
                // Adding all the stories left to the last agent.
                currentAgentWeight = ascSortedStoryWeightMap.values().stream().mapToInt(Integer::intValue).sum();
                assertThat(descSortedStoryWeightMap)
                        .as("Incremental and Decremental story weight maps should be equal after processing")
                        .isEqualTo(ascSortedStoryWeightMap);
                storiesForAgentMap.get(currentAgentNumber).addAll(ascSortedStoryWeightMap.keySet());
            } else {
                // Adding stories to the current agent based of the average scenario weight for each agent
                boolean agentReady = false;
                while (!agentReady && areAnyStoriesLeft(descSortedStoryWeightMap)) {
                    boolean maxAdded = false;
                    boolean minAdded = false;

                    String maxKey = descSortedStoryWeightMap.keySet().iterator().next();
                    Integer maxKeyWeight = descSortedStoryWeightMap.get(maxKey);
                    String minKey = ascSortedStoryWeightMap.keySet().iterator().next();
                    Integer minKeyWeight = ascSortedStoryWeightMap.get(minKey);

                    int averageDeviationForMin = (averageWeight - (currentAgentWeight + minKeyWeight))
                            * (averageWeight - (currentAgentWeight + minKeyWeight)) / averageWeight;
                    int averageDeviationForMax = (averageWeight - (currentAgentWeight + maxKeyWeight))
                            * (averageWeight - (currentAgentWeight + maxKeyWeight)) / averageWeight;

                    if (currentAgentWeight < averageWeight && averageDeviationForMax < averageDeviationForMin) {
                        currentAgentWeight += maxKeyWeight;
                        storiesForAgentMap.get(currentAgentNumber).add(maxKey);
                        descSortedStoryWeightMap.remove(maxKey);
                        ascSortedStoryWeightMap.remove(maxKey);
                        maxAdded = true;
                    }
                    if (currentAgentWeight < averageWeight) {
                        currentAgentWeight += minKeyWeight;
                        storiesForAgentMap.get(currentAgentNumber).add(minKey);
                        ascSortedStoryWeightMap.remove(minKey);
                        descSortedStoryWeightMap.remove(minKey);
                        minAdded = true;
                    }
                    agentReady = !(maxAdded || minAdded);
                }
            }
            agentWeightMap.put(currentAgentNumber, currentAgentWeight);
        }

        LOG.info("Scenario weight for each agent.");
        agentWeightMap.forEach((k, v) -> LOG.info("Agent: " + k + " Weight: " + v));
        LOG.info("Current agent number: {}; Scenario weight: {}", agentNumber, agentWeightMap.get(agentNumber));

        // ungroup stories
        List<String> agentStories = storiesForAgentMap.get(agentNumber);
        List<String> grouppedStories = new ArrayList<>();
        agentStories.forEach(storyPath -> {
            if (storyPath.contains("\n")) {
                grouppedStories.add(storyPath);
            }
        });
        grouppedStories.forEach(storyGroup -> {
            agentStories.remove(storyGroup);
            agentStories.addAll(Arrays.asList(storyGroup.replaceAll("\\n$", "").split("\n")));
        });

        return storiesForAgentMap.get(agentNumber);
    }

    /**
     * Generate map <story path, story weight>. Also group stories by meta index 'concurrency-group'. Grouped stories
     * will be delimited by \n.
     */
    private static Map<String, Integer> generateStoryWeightMap(final List<String> stories) {
        Map<String, Integer> storyWeightMap = new HashMap<>();
        Map<String, String> storyGroupMap = new HashMap<>();

        for (String storyPath : stories) {
            try {
                String storyStr = new String(Files.readAllBytes(Paths.get("src/test/resources/" + storyPath)),
                        StandardCharsets.UTF_8);
                RegexStoryParser storyParser = new RegexStoryParser();
                Story story = storyParser.parseStory(storyStr);
                int storyWeight = 0;
                for (Scenario scenario : story.getScenarios()) {
                    int examplesCount = scenario.getExamplesTable().getRowCount();
                    int cycles = examplesCount == 0 ? 1 : examplesCount;
                    storyWeight += scenario.getSteps().size() * cycles;
                }
                if (story.getMeta().hasProperty("concurrency-group")) {
                    String groupName = story.getMeta().getProperty("concurrency-group");
                    storyGroupMap.put(groupName, storyPath + "\n" + storyGroupMap.getOrDefault(groupName, ""));
                    storyWeight += storyWeightMap.getOrDefault(groupName, 0);
                    storyWeightMap.put(groupName, storyWeight);
                } else {
                    storyWeightMap.put(storyPath, storyWeight);
                }
            } catch (IOException e) {
                LOG.error("Failed to read story: {}", storyPath);
                e.printStackTrace();
            }
        }

        storyGroupMap.forEach((groupName, storyNames) -> {
            Integer storyWeight = storyWeightMap.remove(groupName);
            storyWeightMap.put(storyNames, storyWeight);
        });
        return storyWeightMap;
    }

    private static boolean areAnyStoriesLeft(final Map<String, Integer> storyWeightMap) {
        return !storyWeightMap.isEmpty();
    }
}
