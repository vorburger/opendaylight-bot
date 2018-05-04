* [X] Optimization: skip all projects before the one with the first actual change!

* [X] full TDD for recent code

* [ ] RELENG-106: Order patches by parent

* [X] simplest possible Web UI

* [X] Distribution of Bot: single-JAR or system/ repo - both CLI and/or Web?

* [X] Template engine for both email composition and simple Web UI

* [ ] order Gerrit changes in table already, same as in the magic String

* [ ] RELENG-112 directly provide the git commands for local installation of upstream projects

* [ ] analyze build log results to determine affected project and extract failure

* [ ] RELENG-110 read custom JIRA fields to determine topicName given jiraID

* [ ] link to semi automatically open or even directly launch Build with Parameters Jenkins Job, without having to copy/paste the magic String

* [ ] use email same template engine also for Web UI; for nicer ManagedTopicBuildFailureEmailController & TopicServlet -> TopicController

* [ ] send email (never fully automatically, but propose to human and simple Send button)
        https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-email
        https://docs.spring.io/spring/docs/current/spring-framework-reference/integration.html#mail
        https://www.thymeleaf.org/doc/articles/springmail.html

* [X] changes in "Cannot Merge" state should prevent build from being launched, and be reported

* [ ] check for +1 Verified Vote, and abort if not present

* [ ] check all changes are on the same branch, and abort if not present (and remove "Branch" column from output, just header)

* [ ] have bot as a voting member on Gerrits, -1 all changes on a topic

* [ ] implement simu-merge feature and have bot actually override +2 and Submit

* [ ] replace index.html etc. with a pretty CSS styled Bootstrap or whatever UI

* [ ] extend email templates system to be able to send pretty multipart text + html emails
        https://www.thymeleaf.org/doc/articles/springmail.html
        https://mjml.io
