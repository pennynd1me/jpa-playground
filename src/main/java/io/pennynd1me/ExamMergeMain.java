package io.pennynd1me;

import io.pennynd1me.start.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class ExamMergeMain {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

    public static void main(String[] args) {

        Member member = createMember("memberA", "회원1");

        member.setUsername("회원명변경"); // 준영속 상태에서 변경

        mergeMember(member);
    }

    static Member createMember(String id, String username) {
        EntityManager em1 = emf.createEntityManager();
        EntityTransaction txn1 = em1.getTransaction();

        txn1.begin();

        Member member = new Member();
        member.setId(id);
        member.setUsername(username);
        em1.persist(member);

        txn1.commit();

        em1.close(); // 영속성 컨텍스트1 종료
        // member 엔티티는 준영속 상태가 된다.
        return member;
    }

    static void mergeMember(Member member) {

        EntityManager em2 = emf.createEntityManager();
        EntityTransaction txn2 = em2.getTransaction();

        txn2.begin();
        Member mergeMember = em2.merge(member); // 새로운 영속 상태의 엔티티 반환
        txn2.commit();

        // 준영속 상태
        System.out.println("member = " + member.getUsername());

        // 영속 상태
        System.out.println("mergeMember = " + mergeMember.getUsername());
        System.out.println("em2 contains member = " + em2.contains(member)); // false
        System.out.println("em2 contains mergeMember = " + em2.contains(mergeMember)); // true

        em2.close();
    }

}