package io.pennynd1me;

import io.pennynd1me.start.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class Main {
    // 엔티티 매니저 팩토리 생성
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

    public static void main(String[] args) {

        // 엔티티 매니저 생성
        EntityManager em = emf.createEntityManager();

        // 트랜잭션 획득
        EntityTransaction txn = em.getTransaction();

        try {
            txn.begin();
            logic(em);
            // 트랜잭션 커밋 시 플러시가 자동 호출된다.
            txn.commit();

        } catch (Exception e) {
            txn.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    static void logic(EntityManager em) {

        String id = "id1";
        Member member = new Member();
        member.setId(id);
        member.setUsername("지한");
        member.setAge(2);

        // 등록
        // INSERT SQL을 보내지 않는다.
        em.persist(member);
        System.out.println(member);

        // 수정
        member.setAge(20);

        // 한 건 조회
        // 엔티티를 수정하면 UPDATE SQL을 생성한다.
        Member findMember = em.find(Member.class, id);
        System.out.println(findMember);

        // JQPL 쿼리로 목록 조회
        // 쓰기 지연 SQL 저장소에는 INSERT와 UPDATE 두개의 쿼리가 존재한다.
        // 플러시가 자동 호출되며 두 개의 쿼리를 보낸다.
        List<Member> members = em.createQuery("select m from Member m", Member.class)
                .getResultList();
        System.out.println(members.get(0));

        // 삭제
        em.remove(member);
    }
}