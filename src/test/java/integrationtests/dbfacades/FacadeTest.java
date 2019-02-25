package integrationtests.dbfacades;

import dbfacades.DemoFacade;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/* 
  This is added to demonstrate how you could do integration tests.
  Right now its just a copy of the UNIT tests, which makes sense if you repeat the tests with
  another (real) database.
  Setting up with a real database is considered a RED topic this semester
*/
public class FacadeTest {
  
EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu-test", null);

 DemoFacade facade = new DemoFacade(emf);
// 
//  /** Setup test data in the database to a known state BEFORE Each test */
//  @Before
//  public void setUp() {
//    EntityManager em = emf.createEntityManager();
//    try {
//      em.getTransaction().begin();
//      //Delete all, since some future test cases might add/change data
//      em.createQuery("delete from Car").executeUpdate();
//      //Add our test data
//      Car e1 = new Car("Volve");
//      Car e2 = new Car("WW");
//      em.persist(e1);
//      em.persist(e2);
//      em.getTransaction().commit();
//    } finally {
//      em.close();
//    }
//  }
//
//  // Test the single method in the Facade
//  @Test
//  public void countEntities() {
//    EntityManager em = emf.createEntityManager();
//    try {
//      long count = facade.countCars();
//      Assert.assertEquals(2,count);
//    } finally {
//      em.close();
//    }
//  }
}