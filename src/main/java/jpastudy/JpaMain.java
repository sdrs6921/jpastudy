package jpastudy;

import jpastudy.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpastudy");
        EntityManager manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        try {
            transaction.begin();
            businessLogic(manager);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            manager.close();
        }

        factory.close();
    }

    private static void businessLogic(final EntityManager manager) {
        String id = "id1";
        Member member = new Member();
        member.setId(id);
        member.setUserName("희상");
        member.setAge(1);

        manager.persist(member);

        member.setAge(20);
        Member findMember = manager.find(Member.class, id);
        System.out.println("findMember=" + findMember.getId() + "," +
                findMember.getUserName() + "," + findMember.getAge());
        List<Member> members = manager.createQuery("select m from Member m", Member.class)
                .getResultList();
        System.out.println("members.size=" + members.size());

        manager.remove(member);
    }
}
