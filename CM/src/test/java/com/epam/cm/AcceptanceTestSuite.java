package com.epam.cm;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.epam.cm.core.utils.OsCheck;

import net.serenitybdd.jbehave.SerenityStories;
import net.thucydides.core.guice.Injectors;
import net.thucydides.core.util.EnvironmentVariables;

import org.jbehave.core.io.StoryFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.cm.core.properties.PropertiesNames.*;
import static com.epam.cm.core.utils.WebdriverPath.bindWebdriverPath;

import ch.lambdaj.Lambda;

public class AcceptanceTestSuite extends SerenityStories {
    private static final Logger LOG = LoggerFactory.getLogger(AcceptanceTestSuite.class.getSimpleName());
    private static final String X64_ARCH = "amd64";
    private static EnvironmentVariables environmentVariables = Injectors.getInjector()
            .getProvider(EnvironmentVariables.class).get();

    public AcceptanceTestSuite() {
        try {
            Class.forName("com.epam.cm.core.properties.ProjectProperties");
        } catch (ClassNotFoundException e) {
            LOG.error("Error instantiating ProjectProperties", e);
        }
         bindWebdriverPath();
        selectStoryFilesForRunningSuite();
    }

    private void selectStoryFilesForRunningSuite() {
        String storiesPattern = environmentVariables.getProperty(PROJECT_STORIES);
        if (storiesPattern == null) {
            LOG.info("No suite key or pattern was provided, trying to run all stories in parallel");
            parallelAcceptanceTestSuite(storyPaths());
        } else {
            List<String> suiteStoryPaths = getStoryPathsForSuite(storiesPattern);
            if (suiteStoryPaths.isEmpty()) {
                LOG.info("No suite was found for the given {} key, trying to run as pattern not in parallel",
                        storiesPattern);
                List<String> storyNames = Arrays.asList(storiesPattern.split(";|,"));
                List<String> storyPaths = new ArrayList<>();
                storyNames.forEach(storyName -> storyPaths.add("**/" + storyName));
                parallelAcceptanceTestSuite(storyPaths);
            } else {
                parallelAcceptanceTestSuite(suiteStoryPaths);
            }
        }
    }

    private void parallelAcceptanceTestSuite(final List<String> storyPaths) {
        List<String> stories = new StoryFinder().findPaths(System.getProperty(STORIES_PATH), storyPaths, null);
        // To give matrix jobs an ability to use unique workspaces Batch names are named like on Jenkins:
        // JobName_BatchNumber.
        final String[] agentNumberStrArr = environmentVariables.getProperty(PARALLEL_AGENT_NUMBER, "1").split("_");
        Integer agentNumber = Integer.parseInt(agentNumberStrArr[agentNumberStrArr.length - 1]);
        Integer agentTotal = environmentVariables.getPropertyAsInteger(PARALLEL_AGENT_TOTAL, 1);

        List<String> storiesForThisAgent;

        failIfAgentIsNotConfiguredCorrectly(agentNumber, agentTotal);
        storiesForThisAgent = StoryBatchSorter.getStoriesForCurrentAgent(stories, agentTotal, agentNumber);

        outputWhichStoriesAreBeingRun(storiesForThisAgent);
        findStoriesCalled(Lambda.join(storiesForThisAgent, ";"));
    }

    private List<String> getStoryPathsForSuite(final String runningSuite) {
        File suiteOfStories = findFile(runningSuite, new File(System.getProperty(SUITES_PATH)));
        return collectStoryPathsFromSuiteFile(suiteOfStories);
    }

    private File findFile(String searchedFile, File searchInDirectory) {
        File[] listOfAllFilesInDirectory = searchInDirectory.listFiles();
        File suiteOfStories;
        if (listOfAllFilesInDirectory != null) {
            for (File singleFileFromDirectory : listOfAllFilesInDirectory) {
                if (singleFileFromDirectory.isDirectory()) {
                    suiteOfStories = findFile(searchedFile, singleFileFromDirectory);
                    if (suiteOfStories != null) {
                        return suiteOfStories;
                    }
                } else if (searchedFile.equalsIgnoreCase(singleFileFromDirectory.getName().replaceAll("\\..+$", ""))) {
                    return singleFileFromDirectory;
                }
            }
        }
        LOG.info("There is no suite: {} in directory {}", searchedFile, searchInDirectory);
        return null;
    }

    private List<String> collectStoryPathsFromSuiteFile(final File suiteFile) {
        if (null == suiteFile) {
            return Collections.emptyList();
        }
        List<String> storyPaths;
        try {
            storyPaths = Files.readAllLines(Paths.get(suiteFile.getPath()), Charset.defaultCharset());
        } catch (IOException e) {
            LOG.error("Failed to open suite file, exiting", e);
            throw new RuntimeException(e);
        }
        LOG.info("Got story paths {}", storyPaths);
        return storyPaths;
    }

    private void failIfAgentIsNotConfiguredCorrectly(final Integer agentPosition, final Integer agentCount) {
        if (agentPosition == null) {
            throw new RuntimeException("The agent number needs to be specified");
        } else if (agentCount == null) {
            throw new RuntimeException("The agent total needs to be specified");
        } else if (agentPosition < 1) {
            throw new RuntimeException("The agent number is invalid");
        } else if (agentCount < 1) {
            throw new RuntimeException("The agent total is invalid");
        } else if (agentPosition > agentCount) {
            throw new RuntimeException(String.format(
                    "There were %d agents in total specified and this agent is outside that range (it is specified as %d)",
                    agentPosition, agentCount));
        }
    }

    private void outputWhichStoriesAreBeingRun(final List<String> stories) {
        LOG.info("Running " + stories.size() + " stories: ");
        for (String story : stories) {
            LOG.info(" - {}", story);
        }
    }

    private void setDriverAccordingToOS() {
        OsCheck.OSType ostype = OsCheck.getOperatingSystemType();
        switch (ostype) {
        case Windows:
            setChromeDriverWindows();
            setPhantomJSDriverWindows();
            if (X64_ARCH.equals(System.getProperty("os.arch"))) {
                setIeDriverWindows64();
            } else {
                setIeDriverWindows32();
            }
            break;
        case MacOS:
            setChromeDriverOsx();
            setPhantomJSDriverOsx();
            break;
        case Linux:
            if (X64_ARCH.equals(System.getProperty("os.arch"))) {
                setChromeDriverLinux64();
                setPhantomJSDriverLinux64();
            } else {
                setChromeDriverLinux32();
                setPhantomJSDriverLinux32();
            }
            break;
        case Other:
            LOG.error("Can't define OS");
            break;
        }
    }

    private void setChromeDriverLinux32() {
        System.setProperty("webdriver.chrome.driver", "drivers/linux/32bit/chromedriver");
    }

    private void setChromeDriverLinux64() {
        System.setProperty("webdriver.chrome.driver", "drivers/linux/64bit/chromedriver");
    }

    private void setChromeDriverWindows() {
        System.setProperty("webdriver.chrome.driver", "drivers/windows/chromedriver.exe");
    }

    private void setChromeDriverOsx() {
        System.setProperty("webdriver.chrome.driver", "drivers/osx/chromedriver");
    }

    private void setPhantomJSDriverLinux32() {
        System.setProperty("phantomjs.binary.path", "drivers/linux/32bit/phantomjs");
    }

    private void setPhantomJSDriverLinux64() {
        System.setProperty("phantomjs.binary.path", "drivers/linux/64bit/phantomjs");
    }

    private void setPhantomJSDriverWindows() {
        System.setProperty("phantomjs.binary.path", "drivers/windows/phantomjs.exe");
    }

    private void setPhantomJSDriverOsx() {
        System.setProperty("webdriver.phantomjs.driver", "drivers/osx/phantomjs");
    }

    private void setIeDriverWindows32() {
        System.setProperty("webdriver.ie.driver", "drivers/windows/32bit/iedriver.exe");
    }

    private void setIeDriverWindows64() {
        System.setProperty("webdriver.ie.driver", "drivers/windows/64bit/iedriver.exe");
    }

}
