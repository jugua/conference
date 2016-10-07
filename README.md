<h1>Conference Magagement System by Gang of Five team</h1>

<h2>Wiki</h2>
<p>Wiki is located <a href="">here</a>.</p>

<h2>Virtual Machine</h2>
<p>VM hostname: </p>
<p>SSH with your own domain account.</p>

<h2>Branching Strategy</h2>
 
<p>Below are the main braches of the project: 
  <ul>
    <li>dev [development branch] </li>
    <li>test [testing branch] </li>
    <li>master </li>
  </ul>
 </p>
  
<h3>dev</h3>
<p>The dev branch is to be forked to sub-branches by features, using the exact naming as tasks are named in Jira.</p>
<p>As soon as the feature is ready, a dev/team working on a feature is to issue a Pull Request asking for peer review.</p>
<p>As soon as pull request is approved by at least two peer devs, the branch can be merged to dev.</p>
 
 
<h3>test</h3>
<p>As there is a new version of dev branch ready for testing, dev notify testers. </p>
<p>Upon testers approval (e.g. after the previous testing iteration is over) dev branch is being merged to test.</p>


<h2>Commit Tags</h2>
<p>Start your commit messages with tags in square brackets, making the theme and the reason of the commit clearer for the team.</p>

<p>[tag] description</p>

<p>Example:<br>
[markup] contact page form fixed</p>

<p>Below is the example list of the tags to be used and their meaning, to be extended as agreed by the team:<br>

[npm-install] - new node module added, don't forget to run "npm install" after the pull<br>
[markup] - markup update<br>
[webpack] - webpack config updated</p>
