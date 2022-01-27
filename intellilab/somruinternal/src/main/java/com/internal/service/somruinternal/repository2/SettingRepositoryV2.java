package com.internal.service.somruinternal.repository2;


import java.util.List;

import com.internal.service.somruinternal.model2.SettingV2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface SettingRepositoryV2 extends JpaRepository<SettingV2, Long> {

    @Query(value = "SELECT setting FROM SettingV2 setting where setting.name = :name")
    public SettingV2 getSettingsByName(@Param("name") String name);

    @Query(value = "SELECT setting FROM SettingV2 setting order by setting.name asc")
    public List<SettingV2> getAllSettingsOrderedByName();

    List<SettingV2> findByName(String name);

	public SettingV2 findBySettingId(Long settingId);
}
