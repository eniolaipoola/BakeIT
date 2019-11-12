# TeamLead Meeting Minute
The meeting commenced at 9:35 by Mr Yinka, he highlighted the agenda of the meeting as follows

## Agenda
- Identify all running and upcoming projects for teams, as well as what each individual of the team would be working on.
- Productivity ideas that we will be working towards.
- Discuss on the previous meeting
- Any other business

### Running Projects
- Mobile unit
    * Prime B:
        - Post NBA bug fixes;
            Android: An apk was published for Isreal to test. That is still in testing (Isreal is on leave)
            iOS: Some issues with ApplePay
        - Group members engagement
            Mr Yinka: Currently engaged with some other activities
            Eniola: Working on some team related documents, currently awaiting review

- UI/UX unit
    * Prime Redesign *E-chambers * LawPavilion Website
        These are the projects for the last quarter of the year.
            - Prime redesign will be ready for Ope by next week or two weeks time
            - Segun is currently working on the Ekiti State

- Web Unit
    * Spill over from NBA due to migration from Prime A to B
    * Redevelop some applications e.g JudgeId mapper
    * Redevelop device management system for Prime due to multiple request from customer service, the application is currently in staging
      going into production today.
    * Post NBA support has still been ongoing, the requests are usually researched into and fixed as they come.
        - JudgeId mapper was done by Nana before she left
        - Tofunmi, Benjamin and Nana worked on DNS. Mr AZ helped deploy
        - Support is given by AZ and Benjamin

- Enterprise Application Unit
    * Post NBA support for CaseManager
    * Developing a platform for managing CMS system (Ongoing)
    * Working on improving CMS, feature addition (e.g commenting on a task)
    * Microsoft word office adding for LawReport and SAT
    * FirstBank's project: Handbook for their training which doesn't concern developers; Pilot test is pending for FirstBank
    * Work on data migration for Union Bank
    * Internal Audit at FirstBank: Still in testing
        - Wale is not so engaged currently
        - Isaac is doing general team lead work and also working on email and microsoft word office adding
        - Majority of current tasks is on the interns because they are majorly FE tasks
        - Stephanie is working on comment for tasks and arbitration
        - Jobari is working on the manage engine for CMS
        - Onos is trying to aggregate data for ML
        - Damilare supplies APIs for everyone

     Question: Why does CMS manager not have a repository on gitlab?
     Isaac: Will get back to us

     Is there a documentation on the new feature developed?
     Isaac: No

- Java Unit
    * Post NBA support
    * Getting design framework for Prime Redesign
        - Dele has been working on replicating complex designs so that the implementation of Prime Redesign will be easy
        - Ope has been working with Mr Yinka on the new search engine
        - An  issue was discovered on Prime Mac which Ope will be looking into
        - Ope is working with Mr Yinka to resolve all SAT errors

#### Deductions
 * Web unit can take up a new project
 * Some of the team members on Enterprise Team are  not optimally engaged

#### Action Point
    * Point 1: Isaac should find out about CMS manager's repository and get back to Mr Yinka on the state of the repository
    * Point 2: Mr Yinka should create a repository for the project
    * Point 3: If there is an external repository for it, it should be closed down
    * Point 4: All team leads (Azeez, Yinka, Gabriel, Ope, Isaac) should come up with a recruitment procedure for your team.
        (Graduate Interns, Junior, Intermediate or Senior Developer). Designers: Junior and Senior categories only
        Delivery date: 1st of November.
    * Point 5: Opeyemi and Mr Gabriel Ogunsua should investigate into Graduate Internship and Industrial training curriculum and
      structure. Deadline Friday (18th of October, 2019).
    * Point 6: Team leads should oversee the slide/document of his/her team member's for Knowledge sharing
    * Point 7: Az should give us a documentation by Monday on Knowledge sharing and the whole process that will be involved.
    * Point 8: Gabriel should aggregate their outputs into LawPavilion accounts, he should also request for any requirement
        Deadline: 1st of November
    * Point 9: All team leads should come up with auto-deployment roadmap for their unit
        Deadline: 8th of November
    * Point 10: All team leads that requested for more developers should provide a full description of the skill requirement of
        the roles requested for

### Productivity Ideas
    - Observations
        * Quality with how we do things varies within teams; a minimum level of standards should be achieved across teams
        * Recruitment Procedure should include
            - The interview procedure for each level of recruitment.
            - Questions for interview when applicant comes to our workspace
            - Code interviews
            - Graduate Internship and Industrial Training
            - Internship curriculum
                * Should interns be run through a particular procedure?
                * Intern developer/Graduate Internship, graduates with basic development skills.
        * Do stakeholders really understand the process
        * Knowledge Sharing: To help bridge knowledge gaps across units.
            - Idea: Sharing code related knowledge with team members.
            - chairman knowledge sharing committee: Azeez
            - Frequency: Every week, Thursday.
            - Duration: 1hr - 1hr 30mins, Time: 12pm - 1pm
            - Suggestion: At one of our knowledge sharing session, let's sensitize developers on our in-house projects.


        * Gitlab Usage
        * Git Workflow and code review
        * Writing Tests
        * Auto-deployment
        * Weekly reports


### Projects

- Prime
    * Interface redesign to include AI suite aggregation
        - UI/UX team:
            Scope: Redesign Prime on PC
            Done: AI Suite Aggregation
            Development Process: Developed for reader, search, dashboard for PC
            Requirement: Product requirement document from PM, User's story
            Pending: Textbook and Journals and Words in Gold
            Timeline: Monday 21st of October, tentatively.

        - Search engine redesign
            Target: First experimental outcome from search engine brainstorming
            Timeline: 2nd week in November, tentatively

        - Textbook and Journal
            * Components: Android app, iOS app, Windows app, Mac app, Online bookstore(API, cloud storage),
                content manager.
            * Requirements:
                - Full documentation (All type and forms of documentation required)
                - Product manager should be named for the project
            * Teams Involved: UI/UX, Mobile, and Java unit
            * Concerns:
                - UI/UX team needs More hands (Junior UI/UX designer).
                - How early do we get the documentation redy
            * Timeline: Android and iOS will take 3 months each(Proper UI, API calls, Unit and
                - UI tests, clean codes), given that all requirements are available (mockup, APIs)
                - Windows and Mac PC will take 4months

        - E-chambers
            - Requirements: Documentation (All forms of documentation)
            - Timeline: It will take the UI/UX team 2-3 months considering that they are 3 people in the team.

        - Data Refactoring and Data Encryption
            * Suggestion:
                - We should understand all data refactoring and encryption entail, on both the developers and managemrt"s side
                - Come up with a team that will dedicate time to visit our data weekly.
                - Another suggestion is to make data refactoring a project on it's own.
                - The threat level of our current data is identified to be very high

            * Stages
                - Identify all data and the servers they are on
                - Identify their sizes and how big they are (no of rows, size)
                - Identify the interrelationship between our data
                - Identify how it's structure can be made better
                - Scope out how the refactoring affects our current products

            * Required Team Member Skills
                - People that has an in-depth knowledge of our products
                - People with database management skills
                - Recommendations are; Mr Laloye, Mr Yinka, Tofunmi, Damilare, Mr Azeez

        * Prime Online
            All forms of documentation


- S.A.T
    * Tests
    * 32 bit installer
    * S.A.T online;
            All forms of documentation
    * Arbitration practise documentation

- CourtManager
- E-Adjust
- CaseManager
- BuildSpec
- KnowMyRight
- Newer
- RytHand
    - All requires appropriate documentation

- CRM
    - Received compliant about report; the explains should be well documented and clarified
    - ERP extension

    N.B: There is a need for LP-Support systems which will be a sopport for all out platforms.

    #### Action Point
    * Point 1: Documentation(BRD) for Text and Journal.
    * Point 2: _Validate if management still want interapp communication_
    * Point 3: Validate if management still have LP-Store in mind or not

General Requirement
- Java unit
    - 2 intermediate developers
    - Highend systems in terms of screen resolution.
    - A larger workspace
    - Macbook for testing

- UI/UX Unit
    - 2 high end monitor
    - 1 UI/UX designer (Junior UI/UX designer).

- Web Unit
    - 2 Intermediate full stack developer for the web unit(proficient in both frontend and backend)
    - 1 Senior full stack developer for the web unit

- Mobile Unit
    - 2 Intermediate android developer
    - 1 Intermediate iOS developer