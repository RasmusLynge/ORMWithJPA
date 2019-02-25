package utils;

import javax.persistence.Persistence;
public class Tester {
  
  public static void main(String[] args) {
    System.out.println("Building the Table(s)");
    Persistence.generateSchema("pu", null);
  }
  
}
