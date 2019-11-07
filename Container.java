public class Container extends ContainerBase
{
   public Container()
   {
      super();
   }
   
   public void add(Object a, boolean b)
   {
      try
      {
         System.out.println("Adding new Node containing: " + a.toString() + "\n");
         
         if (this.size() > 0)
         {
            if ( b )
            {
               this.addFront(a);
            }
            else
            {
               this.addBack(a);
            }
         }
         else
         {
            this.addFirst(a);
         }
      }
      catch (Exception e)
      {
         throw new ContainerException("Add to container failed: " + e.toString() );
      }
   }
}

class ContainerBase implements ContainerInterface
{   
   private Node head;
   private Node tail;
   private int item;
   
   // default constructor sets item count to 0 and inserts it into new head node
   public ContainerBase()
   {
      item = 0;
      head = new Node(item);
   }
   
   // method to see if container is empty
   public boolean isEmpty()
   {
      return item == 0;
   }
   
   // method to find size of the container
   public int size()
   {
      return item;
   }
   
   // hidden method used to alter the size of container
   private void changeSize(boolean flag)
   {
      // if flag is true then increment container and store data in head node
      if ( flag )
      {
         item++;
         head.setInfo(size());
      }
      else // otherwise decrement container and store data in head node
      {
         item--;
         head.setInfo(size());
      }
   }
   
   // method used to find specific nodes based on data held within
   public Node find(Object a)
   {
      // create temp nod e and set to the next node following head node
      Node temp = head.getNext();
      String s1; // declare 2 string variables for later use
      String s2;
      
      // check to see if the data held is an int value of something else
      // then process s1 variable accordingly
      if ( a instanceof Integer )
      {
         s1 =  String.valueOf( a );
      }
      else
      {
         s1 = (String)a;
      }
      
      // loop through the nodes held within the container
      while ( temp != head )
      {
         // set s2 var to data held within temp node
         s2 = String.valueOf(temp.getInfo());
         
         // check to see if the data held matches
         if ( s1.equals( s2 ) )
         {
            // if true then return temp node
            return temp;
         }
         else if ( s2.startsWith( s1 ) )
         {
            return temp;
         }
         else // otherwise continue to search
         {
            temp = temp.getNext();
         }
      }
      
      // if data not found then null is returned
      return null;
   }
   
   // this method adds the first node to the container
   public void addFirst(Object a)
   {
      // create new tail node and set its prev/next fields to head node
      tail = new Node(a, head, head);
      
      // set the head nodes next/prev fields to the new tail node
      head.setNext(tail);
      head.setPrev(tail);
      
      // use the changeSize() method to increment size of container
      changeSize(true);
   }
   
   // method that allows data to be added to back of container
   public void addBack(Object a) throws ContainerOutOfBoundsException
   {
      // create new temp node and initialize next field to head nodes
      // next field while setting prev field to head node.
      Node temp = new Node();
      temp.setInfo(a);
      temp.setNext(head.getNext());
      temp.setPrev(head);
      
      // get head nodes next field and set its prev field to temp node
      // then set head nodes next field to temp.
      head.getNext().setPrev(temp);
      head.setNext(temp);
      
      // use changeSize() method to increment size of container
      changeSize(true);
   }
   
   // adds a node to the front of the container head node
   public void addFront(Object a) throws ContainerOutOfBoundsException
   {
      // create new tail node setting prev to last tail node
      // and setting next to the head node.
      tail = new Node(a, tail, head);
      
      // set the previous tail nodes next field to new tail node and
      // set the head nodes prev field to the new tail node.
      tail.getPrev().setNext(tail);
      head.setPrev(tail);
      
      // call change size function to alter size of elements in container
      changeSize(true);
   }
   
   // remove a Node from the container
   public void remove(Object a)
   {
      // set up temp node for later use
      Node temp;
      
      // make sure there are containers to remove
      if ( !isEmpty() )
      {
         // use find method to locate the node that needs to be removed
         temp = find(a);
         
         // if find method was sucessful process accordingly
         if (temp != null)
         {
            // get the address of the previous node held in temp 
            // and set it that nodes next address to temps next
            // address. follow similar pattern for setting prev.
            temp.getPrev().setNext(temp.getNext());
            temp.getNext().setPrev(temp.getPrev());
            
            // use the changeSize() method to decrement size of container
            changeSize(false);
            
            // print out the data being deleted to show process was completed sucessfully
            System.out.println("Node containing " + a.toString() + " has been removed.\n");
         }
         else
         {
            // if node is not found then inform user of this error
            System.out.println("Node containing " + a.toString() + " does not exist.\n");
         }
      }
      else
      {
         // if container is empty then inform user
         System.out.println("Container is already empty..\n");
      }
   }
   
   // reset the containers back to being empty
   public void removeAll()
   {
      item = 0;
      head = new Node(item, head, head);
      tail = null;
   }
   
   // prints contents of containers using a boolean to determine
   // which way to print the data: front to back or back to front
   public void print(boolean flag)
   {
      // temp node declared for later use
      Node temp;
      
      // check to see if the containers are empty
      if ( isEmpty() )
      {
         System.out.println("Containers are currently empty.");
      }
      else 
      {
         // if flag is true then process data starting at front
         if (flag)
         {
            // set temp node to head node as starting point
            temp = head.getNext();
            
            // use do while loop structure to avoid an early false
            // for the while statement
            do
            {
               System.out.println(temp.getInfo());
               temp = temp.getNext();
            }
            while (temp != head); // end when temp == head
         }
         else // else start from the back 
         {
            // set temp node to head same as above
            temp = head.getPrev();
            
            // same as above except for the temp nodes are following
            // the prev node instaed of the next node.
            do
            {
               System.out.println(temp.getInfo());
               temp = temp.getPrev();
            }
            while (temp != head);
         }
      }
      System.out.println();
   }
}

// interface for the ContainerBase
interface ContainerInterface
{
   public boolean isEmpty();
   public int size();
   public Node find(Object a);
   public void addFirst(Object a);
   public void addBack(Object a);
   public void addFront(Object a);
   public void remove(Object a);
   public void removeAll();
   public void print(boolean flag);
}

// Node class with extra data used to show Nodes in either direction
class Node
{
   private Object info;
   private Node prev;
   private Node next;
   
   public Node()
   {
      info = null;
      next = null;
      prev = null;
   }
   
   public Node(Object info)
   {
      this.info = info;
      next = null;
      prev = null;
   }
   
   // initialize new node with given characteristics (info, prev, next)
   public Node(Object info, Node p, Node n)
   {
      this.info = info;
      next = n;
      prev = p;
   }
   
   public Object getInfo()
   {
      return info;
   }
   
   public Node getNext()
   {
      return next;
   }
   
   public Node getPrev()
   {
      return prev;
   }
   
   public void setInfo(Object i)
   {
      info = i;
   }
   
   public void setNext(Node n)
   {
      next = n;
   }
   
   public void setPrev(Node p)
   {
      prev = p;
   }
}

// Exception class created incase the index given is out of the given bounds
class ContainerOutOfBoundsException extends IndexOutOfBoundsException
{
   public ContainerOutOfBoundsException(String s)
   {
      super(s);
      System.err.println("Error has occured");
   }
   private final static long serialVersionUID = 2019L;
}

// Exception class used to add my own little spin on the RuntimeException class
class ContainerException extends RuntimeException
{
   public ContainerException(String s)
   {
      super(s);
      System.err.println("Error has occured");
   }
   private final static long serialVersionUID = 2018L;
}

class CarData
{
   private String make;
   private int doors;
   
   public CarData(String make, int doors)
   {
      this.make = make;
      this.doors = doors;
   }
   
   public String getData()
   {
      return make + " " + doors + " doors";
   }
   
   public void setData(String make, int doors)
   {
      this.make = make;
      this.doors = doors;
   }
   
   public String getMake()
   {
      return make;
   }
   
   public int getDoors()
   {
      return doors;
   }
   
   public void setMake(String make)
   {
      this.make = make;
   }
   
   public void setDoors(int doors)
   {
      this.doors = doors;
   }
}

class PlaneData extends CarData
{
   private int engine;
   
   public PlaneData(String make, int doors, int engine)
   {
      super(make,doors);
      this.engine = engine;
   }
   
   public String getData()
   {
      return super.getData() + " " + engine + " engines.";
   }
   
   public void setData(String make, int doors, int engine)
   {
      super.setData(make,doors);
      this.engine = engine;
   }
   
   public void setEngine(int engine)
   {
      this.engine = engine;
   }
   
   public int getEngine()
   {
      return engine;
   }
}

class Driver
{
   public static void main(String [] args)
   {
      // B/C Option Data
      // integers added to the Container
      Container a = new Container();
      a.add(33,true);
      a.add(57,true);
      a.add(85,false);
      a.add(62,false);
      a.add(95,true);
      a.print(true);
      a.print(false);
      a.remove(57);
      a.remove(33);
      a.remove(33);
      a.remove(62);
      a.add(22,true);
      a.remove(95);
      a.print(true);
      
      // A Option Data
      // Objects added to the Container
      CarData car = new CarData("Ford",4);
      PlaneData plane = new PlaneData("Boeing",3,6);
      a.add(car.getData(),false);
      car.setDoors(2);
      a.add(car.getData(),true);
      car.setMake("GMC");
      a.add(car.getData(),false);
      car.setMake("RAM");
      a.add(car.getData(),false);
      car.setData("Chevy",3);
      a.add(car.getData(),true);
      a.print(true);
      a.remove("Ford");
      System.out.println("Number of items: " + a.size() + "\n");
      a.print(true);
      a.add(plane.getData(),true);
      plane.setData("Piper",2,1);
      a.add(plane.getData(),true);
      plane.setData("Cessna",4,4);
      a.add(plane.getData(),true);
      a.print(true);
      a.removeAll();
      a.print(true);
   }
}