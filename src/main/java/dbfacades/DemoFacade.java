package dbfacades;

import entity.Customer;
import entity.CustomerOrder;
import entity.ItemType;
import entity.OrderLine;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/*
Spørgsmål 1: 
Explain the rationale behind the topic Object Relational Mapping and the Pros and Cons in using ORM: 
Object-Relational Mapping (ORM) er en teknik, som lader dig manipulere dine queries og data fra din database via objekt orienteret programmering  
Pros: Redution i kode, ?
Cons: Svært at lære (nogle menere at den er svært at manipulere med databasen(database navne mm.) det kan gøres med @table @column osv.

Spørgsmål 2:
Explain the JPA strategy for handling Object Relational Mapping and important classes/annotations involved.:
Entity class: er nok den vigtigste. netbeans wizzard kan lave en skabelon for dig, som hjælper med at få din entitet til databasen. (vis en entitests klasse og database kald for at vise annotationer)

Spørgsmål 3:
Outline some of the fundamental differences in Database handling using plain JDBC versus JPA:
JDBC er standard for Database Access (high level)
JPA er standard for ORM (low level)

Spørgsmål 4:
Explain some of the problems which occur when you write tests that involves database operations:
Jeg har glemt det :i
jeg gætter på at det er fordi man også bliver nød til at lave en test database ?

Spørgsmål 5: 
Explain ways (one strategy is enough) to mock away the database when writing unit tests.
Lave en test-Percistence fil med en test forbindelse, som man kan kalde "in memory" og teste på

Spørgsmål 6:
Explain ways to run integration tests on Travis using a “real” MySQL server:
Lav en test sql database?

 */
public class DemoFacade {

    EntityManagerFactory emf;

    public DemoFacade(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public Customer addCustomer(Customer customer) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(customer);
            em.getTransaction().commit();
            return customer;
        } finally {
            em.close();
        }
    }

    public List<Customer> getAllCustomers() {
        EntityManager em = emf.createEntityManager();
        try {
            return (List<Customer>) em.createQuery("select m from Customer m").getResultList();
        } finally {
            em.close();
        }
    }

    long countCustomers() {
        EntityManager em = emf.createEntityManager();
        try {
            return (Long) em.createQuery("select Count(m) from Customer m").getSingleResult();
        } finally {
            em.close();
        }
    }

    private Customer getCustomer(String name) {
        EntityManager em = emf.createEntityManager();
        try {
            Query q = em.createQuery("SELECT e FROM Customer e WHERE e.name =:name");
            q.setParameter("name", name);
            return (Customer) q.getSingleResult();
        } finally {
            em.close();
        }
    }

    private CustomerOrder addCustomerOrder(CustomerOrder customerOrder) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(customerOrder);
            em.getTransaction().commit();
            return customerOrder;
        } finally {
            em.close();
        }
    }

    private CustomerOrder addOrderToCustomer(String name) {
        CustomerOrder cOrder = new CustomerOrder();
        EntityManager em = emf.createEntityManager();
        Customer c = getCustomer(name);
        cOrder.setCustomer(c);
        try {
            em.getTransaction().begin();
            em.persist(cOrder);
            em.getTransaction().commit();
            return cOrder;
        } finally {
            em.close();
        }
    }

    private CustomerOrder getCustomerOrder(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            Query q = em.createQuery("SELECT e FROM CustomerOrder e WHERE e.id =:id");
            q.setParameter("id", id);
            return (CustomerOrder) q.getSingleResult();
        } finally {
            em.close();
        }
    }

    private List<CustomerOrder> getCustomerOrders(Customer customer) {
        EntityManager em = emf.createEntityManager();
        try {
            Query q = em.createQuery("SELECT e FROM CustomerOrder e WHERE e.customer =:Customer");
            q.setParameter("Customer", customer);
            return (List<CustomerOrder>) q.getResultList();
        } finally {
            em.close();
        }
    }

    private OrderLine addOrderLine(OrderLine orderLine) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(orderLine);
            em.getTransaction().commit();
            return orderLine;
        } finally {
            em.close();
        }
    }

    private OrderLine getOrderLine(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            Query q = em.createQuery("SELECT e FROM OrderLine e WHERE e.id =:id");
            q.setParameter("id", id);
            return (OrderLine) q.getSingleResult();
        } finally {
            em.close();
        }
    }

    private ItemType addItemType(ItemType iType) {
        EntityManager em = emf.createEntityManager();
        
        try {
            em.getTransaction().begin();
            em.persist(iType);
            em.getTransaction().commit();
            return iType;
        } finally {
            em.close();
        }
    }
    private List<OrderLine> getOrderLines(CustomerOrder cOrder) {
        EntityManager em = emf.createEntityManager();
        try {
            Query q = em.createQuery("SELECT e FROM OrderLine e WHERE e.customerOrder =:cOrder");
            q.setParameter("cOrder", cOrder);
            return (List<OrderLine>) q.getResultList();
        } finally {
            em.close();
        }
    }

    private double getTotalPriceOrder(CustomerOrder cOrder) {
        int price = 0;
        List<OrderLine> oLines = getOrderLines(cOrder);
        for (int i = 0; i < oLines.size(); i++) {
            int quantity = oLines.get(i).getQuantity();
            double itemPrice = oLines.get(i).getItemType().getPrice();
            price += +quantity*itemPrice;
        }
        return price;
    }

    /*
  This will only work when your have added a persistence.xml file in the folder: 
     src/main/resources/META-INF
  You can use the file: persistence_TEMPLATE.xml (in this folder) as your template
     */
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        DemoFacade df = new DemoFacade(emf);
        //Opgave 1 -- Create a Customer
        df.addCustomer(new Customer("Klavs", "email"));
        df.addCustomer(new Customer("Hans", "email"));
        df.addCustomer(new Customer("Rasmus", "email"));
        //Opgave 2 -- Find a Customer
        System.out.println("Opgave2: find a customer: \n"+ df.getCustomer("Klavs"));
        //Opgave 3 -- Get all Customers
        System.out.println("Opgave3: find all customers: \n"+ df.countCustomers());
        //Opgave 4 -- Create an Order 
        df.addCustomerOrder(new CustomerOrder());
        //Opgave 5 -- Add an Order to a Customer
        df.addOrderToCustomer("Hans");
        df.addOrderToCustomer("Klavs");
        CustomerOrder o = df.addOrderToCustomer("Klavs");
        //Opgave 6 -- Find an Order
        CustomerOrder cOrder = df.getCustomerOrder(1);
        System.out.println("Opgave6: find an order: \n"+ cOrder);
        //Opgave 7 --  Find all Orders, for a specific Customer
        System.out.println("Opgave7: find all orders for a customer: \n"+df.getCustomerOrders(df.getCustomer("Klavs")));
        //Opgave 8 -- Create an OrderLine, and add it to an Order
        df.addOrderLine(new OrderLine(3, df.getCustomerOrder(2)));
        //Opgave 9 -- Create an ItemType, and add it to an OrderLine    
        df.addItemType(new ItemType("pc", "en computer", 10000));
        ItemType iType = df.addItemType(new ItemType("item", "Description", 99));
        df.addItemToOrderLine(cOrder, iType);
        //Opgave 10 -- find total price of an order
        double totalprice = df.getTotalPriceOrder(cOrder);
        System.out.println("Opgave10: find price of an order: \n"+totalprice);
    }


    //Adder ikke til specifik orderLine men laver en ny OrderLine :(
    private OrderLine addItemToOrderLine(CustomerOrder cOrder, ItemType iType) {
        EntityManager em = emf.createEntityManager();
        OrderLine oLine = new OrderLine(2, cOrder, iType);

        try {
            em.getTransaction().begin();
            em.persist(oLine);
            em.getTransaction().commit();
            return oLine;
        } finally {
            em.close();
        }
    }

}
