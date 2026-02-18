/*package com.elanur_sude.gonulluluk_proje.repository;

import com.elanur_sude.gonulluluk_proje.model.Participation;
import com.elanur_sude.gonulluluk_proje.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {

    List<Participation> findByProject_Id(Long projectId);
    long countByStatus(Status status);

    boolean existsByProject_IdAndUser_Id(Long projectId, Long userId);
}*/

package com.elanur_sude.gonulluluk_proje.repository;

import com.elanur_sude.gonulluluk_proje.model.Participation;
import com.elanur_sude.gonulluluk_proje.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {

    List<Participation> findByProject_Id(Long projectId); //Belirli bir projeye ait tüm katılımcıları döner.
    long countByStatus(Status status); //Toplam katılım sayısı
    boolean existsByProject_IdAndUser_Id(Long projectId, Long userId);//Bir kullanıcı aynı projeye ikinci kez katılmış mı?

// Katılımcı sayısına göre en popüler projeleri döndürür
@Query("SELECT p.name, COUNT(pa.id) as participantCount " +  //Normal SQL değil, entity sınıfları üzerinden çalışır.
       "FROM Participation pa " +
       "JOIN pa.project p " +
       "WHERE pa.status = com.elanur_sude.gonulluluk_proje.model.Status.APPROVED " +
       "GROUP BY p.name " +
       "ORDER BY participantCount DESC")//Katılımcı sayısına göre azalan sırada döner
List<Object[]> findTopProjects(org.springframework.data.domain.Pageable pageable);//Dönen veri tablo gibi olur.

}
