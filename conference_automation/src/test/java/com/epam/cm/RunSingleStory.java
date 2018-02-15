package com.epam.cm;

/**
 * Class for single story debug
 */
public class RunSingleStory extends AcceptanceTestSuite {


    final String CUR_STORY = "stories/account/Logout.story";

    @Override
    protected String getStoryPath() {
        return CUR_STORY;
    }

}
