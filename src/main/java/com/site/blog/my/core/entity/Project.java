package com.site.blog.my.core.entity;


import lombok.Data;

@Data
public class Project {
    private Integer projectId;
    private Integer projectRank;
    private String  projectName;
    private String  projectUrl;
    private String  projectEnvironment;
    private String  projectTechnology;
    private String  projectIntroduce;
    private String  projectFunctionality;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", projectId=").append(projectId);
        sb.append(", projectRank=").append(projectRank);
        sb.append(", projectName=").append(projectName);
        sb.append(", projectUrl=").append(projectUrl);
        sb.append(", projectEnvironment=").append(projectEnvironment);
        sb.append(", projectTechnology=").append(projectTechnology);
        sb.append(", projectIntroduce=").append(projectIntroduce);
        sb.append(", projectFunctionality=").append(projectFunctionality);
        sb.append("]");
        return sb.toString();
    }
}
