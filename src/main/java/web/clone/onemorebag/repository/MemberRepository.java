package web.clone.onemorebag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.clone.onemorebag.entity.member.Member;



@Repository
public interface MemberRepository extends JpaRepository<Member, String> {

}
