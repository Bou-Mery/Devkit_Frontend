package ma.ensa.mobile.devkit.dao;

import java.util.List;

import ma.ensa.mobile.devkit.services.FrameworkService;

public interface IDao<T> {
    boolean addFramework(T obj ,FrameworkService.AddFrameworkCallback callback);
    boolean deleteFramework(int id , FrameworkService.DeleteFrameworkCallback callback);
    boolean updateFramework(T obj , FrameworkService.UpdateFrameworkCallback callback);

    T findFrameworkById(int id);
    void findAll(FrameworkService.FrameworkCallback callback);

}
