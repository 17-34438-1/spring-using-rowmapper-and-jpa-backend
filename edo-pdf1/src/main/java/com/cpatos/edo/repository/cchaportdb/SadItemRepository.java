package com.cpatos.edo.repository.cchaportdb;

import com.cpatos.edo.model.cchaportdb.SadItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SadItemRepository extends JpaRepository<SadItem, Long> {

    @Query("select i.id from SadItem s INNER JOIN s.sadInfo i where s.sumDeclare=:igmBlNo")
    Long getSadIdBySumDeclare(@Param("igmBlNo") String igmBlNo);

//    @Query("select s.sadId from SadItem s where s.sumDeclare=:igmBlNo")
//    Long getSadIdBySumDeclare(@Param("igmBlNo") String igmBlNo);

    List<SadItem> findBySumDeclare(String sumDeclare);

}
