export class Automation {
    id: string;
    jiraProject: string;
    types: string[];
    createdAt: string;
  
    constructor(
      id: string,
      jiraProject: string,
      types: string[],
      createdAt: string

    ) {
      this.id = id;
      this.jiraProject = jiraProject;
      this.types = types;
        this.createdAt = createdAt;
    }
  }
  