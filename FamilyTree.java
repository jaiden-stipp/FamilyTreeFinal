import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FamilyTree implements Serializable {
    private List<Person> roots;

    public FamilyTree() {
        this.roots = new ArrayList<>();

    }

    public List<Person> getRoots() {
        return roots;
    }

    public void addMember(Person newMember, Person parent) {
        if (parent != null) {
            parent.addChild(newMember);
        } else if(!roots.contains(newMember)) {
            roots.add(newMember);
        }
    }
    public void removeMember(Person member) {
        for (Person parent : member.getParents()) {
            parent.getChildren().remove(member);
        }
        for (Person child : member.getChildren()) {
            child.getParents().remove(member);
        }
        roots.remove(member);
    }

    public void printFamilyTree(Person person, int depth) {
        if (person == null) return;
        for (int i = 0; i < depth; i++) {
            System.out.print("  ");
        }
        String spouseInfo = (person.getSpouse() != null) ?  "  <3  " + person.getSpouse().getName() + " (" + person.getSpouse().getAge() + ")" : "";
        System.out.println(person.getName() + " (" + person.getAge() + ")" + spouseInfo);
        for (Person child : person.getChildren()) {
            printFamilyTree(child, depth + 1);
        }
    }
    public void printFamily() {
        for(Person root : roots) {`     
            printFamilyTree(root,0);
        }
    }
}
