import java.util.List;
import java.util.Scanner;

public class MainWindow {
    private FamilyTree familyTree;
    private Scanner sc;

    public MainWindow(FamilyTree familyTree) {
        this.familyTree = familyTree;
        this.sc = new Scanner(System.in);
    }
    public void startMenu() {
        System.out.print("\033[H\033[2J");
        System.out.flush(); 
        sc = new Scanner(System.in);
        String input;
        System.out.println("Welcome to the Family Tree App!\n");
        System.out.println("Here is your current family tree: \n");
        displayFamilyTree();
        System.out.println("\nAvailable Actions: ");
        System.out.println("1. Add Member");
        System.out.println("2. Remove Member");
        System.out.println("3. Connect Members");
        System.out.println("4. Exit\n");
        System.out.print("Enter your choice: ");
        input = sc.nextLine();
        switch(input) {
            case "1" :
                addMemberPrompt();
                break;
            case "2" :
                removeMemberPrompt();
                break;
            case "3" :
                connectMembersPrompt();
                break;
            case "4":
                System.out.println("Exiting...");
                break;
            default: 
                System.out.println("Invalid option. Please choose a number between 1 and 5");
                startMenu();
                break;
                


        }

    }
    public void addMemberPrompt() {
        System.out.print("\033[H\033[2J"); 
        System.out.flush();
        displayFamilyTree();
        System.out.print("\nEnter the name of the new member: ");
        String name = sc.nextLine();
        System.out.print("Enter the age of the new member: ");
        int age = Integer.valueOf(sc.nextLine());
        Person newMember = new Person(name, age);
        System.out.print("Enter this person's parent (If none, type none and they will be added as a root of the family tree): ");
        String parentCheck = sc.nextLine();
        switch(parentCheck) {
            case "none":
            case "None":
            case "NONE":
                familyTree.addMember(newMember, null);
                break;
            default:
                Person parent = findPersonByName(parentCheck);
                familyTree.addMember(newMember, parent);
        }
        System.out.println("Member added successfully!");
        startMenu();
    }
    public void removeMemberPrompt() {
        System.out.print("\033[H\033[2J"); 
        System.out.flush();
        displayFamilyTree();
        System.out.print("Enter the name of the member to remove: ");
        String name = sc.nextLine();
        Person member = findPersonByName(name);
        if (member != null) {
            familyTree.removeMember(member);
            System.out.println("Member removed successfully.");
        } else {
            System.out.println("Member not found.");
        }
        startMenu();
    }
    private void connectMembersPrompt() {
        System.out.print("\033[H\033[2J"); 
        System.out.flush();
        displayFamilyTree();
        System.out.print("Enter the name of the first member to connect: ");
        String spouse1 = sc.nextLine();
        System.out.print("Enter the name of the second member to connect: ");
        String spouse2 = sc.nextLine();
    
        if (spouse1.equalsIgnoreCase(spouse2)) {
            System.out.println("Cannot connect a person to themselves.");
            return;
        }
    
        if (checkForCommonParents(spouse1, spouse2)) {
            System.out.println("Cannot connect members as spouses due to them sharing at least one parent.");
            return;
        }
    
        connectSpouses(spouse1, spouse2);
        startMenu();
    }
    private boolean checkForCommonParents(String name1, String name2) {
        Person p1 = findPersonByName(name1);
        Person p2 = findPersonByName(name2);
       

        List<Person> parents1 = p1.getParents();
        List<Person> parents2 = p2.getParents();

        for (Person parent1 : parents1) {
            if (parents2.contains(parent1)) {
                return true;  // Return true if they share at least one parent.
            }
    }

    return false;
    }
    public void connectSpouses(String spouse1, String spouse2) {
        Person s1 = findPersonByName(spouse1);
        Person s2 = findPersonByName(spouse2);
    
        if (s1 == null || s2 == null) {
            System.out.println("One or both of the persons could not be found.");
            return;
        }
    
        if (s1.getSpouse() != null || s2.getSpouse() != null) {
            System.out.println("One or both of the persons are already married.");
            return;
        }
    
        s1.addSpouse(s2);
        System.out.println(spouse1 + " and " + spouse2 + " are now married.");
    }
    private void displayFamilyTree() {
        familyTree.printFamily();
    }
    
    private Person findPersonByName(String name) {
        for(Person root : familyTree.getRoots()) {
            Person found = findPersonRecursive(root, name);
            if(found != null) {
                return found;
            }
        }
        return null;
    }
    private Person findPersonRecursive(Person current, String name) {
        if (current == null) return null;
        if (current.getName().equalsIgnoreCase(name)) return current; 
        // Recursively search in each child
        for (Person child : current.getChildren()) {
            Person found = findPersonRecursive(child, name);
            if (found != null) return found; 
        }
        return null; 
    }
}