# CyberSentry

An Incident Reporting API that is designed to manage the reporting, tracking, and resolution of incidents in an organization or community. It’s particularly useful for organizations that need to monitor, log, and respond to various incidents such as IT issues and security breaches.

# Key Features

1. User Authentication and Authorization
    * OAuth 2.0
    * Role Based Access
      * **Admin** : Can view, update, close all incidents, assign incidents to users.
      * **Assignee(Resolver)** : Can view assigned incident and update its status

2. Incident Management
   * Create incident
     * Users can report new incidents by providing incident details and affected systems
   * CRUD Operations
     * **Create** : Report a new incident
     * **Read** : View incidents by various filters (e.g. status)
     * **Update** : Modify incident details such as status
     * **Delete** : Admin can delete irrelevant or false reports
   * Incident Assignment
     * Admins can assign incidents to specific users/group of users responsible for resolving the issue
     * Track the progress of the assignee in resolving the incident
