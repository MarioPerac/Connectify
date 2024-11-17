export class Automation {
    id: string;
    jiraProject: string;
    typesName: string;
    createdAt: string;
  
    constructor(
      id: string,
      jiraProject: string,
      typesName: string,
      createdAt: string

    ) {
      this.id = id;
      this.jiraProject = jiraProject;
      this.typesName = typesName;
        this.createdAt = createdAt;
    }
  }
  