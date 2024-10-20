package ma.ensa.mobile.devkit.beans;

import java.io.Serializable;

public class Framework implements Serializable {

    private int id ;
    private String name ;
    private String descreption;
    private String domain ;
    private String dependencies ;
    private String image_path;

    public Framework(int id, String name,
                     String descreption,
                     String domain,
                     String dependencies,
                     String image_path) {
        this.id = id;
        this.name = name;
        this.descreption = descreption;
        this.domain = domain;
        this.dependencies = dependencies;
        this.image_path = image_path;
    }

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescreption() {
        return descreption;
    }

    public void setDescreption(String descreption) {
        this.descreption = descreption;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getDependencies() {
        return dependencies;
    }

    public void setDependencies(String dependencies) {
        this.dependencies = dependencies;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }
}
