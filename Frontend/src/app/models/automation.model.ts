export class Automation {
    id: string;
    jiraProject: string;
    types: string[];
    createdAt: string;
    status: boolean;
    constructor(
      id: string,
      jiraProject: string,
      types: string[],
      createdAt: string,
      status: boolean

    ) {
      this.id = id;
      this.jiraProject = jiraProject;
      this.types = types;
      this.createdAt = createdAt;
      this.status = status;
    }
  }
  