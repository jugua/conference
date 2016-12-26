package com.epam.cm;

/**
 * Class for single story debug
 */
public class RunSingleStory extends AcceptanceTestSuite {


    final String CUR_STORY = "stories/debug.story.old";

    @Override
    protected String getStoryPath() {
        return CUR_STORY;
    }

}
