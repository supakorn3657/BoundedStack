import java.util.ArrayList;
/////////   นายศุภกร รุ่งสุวรรณสกุล 6821601488   ///////
public class BoundedStack {
    
    // TODO: อย่าลืมเขียน AF และ RI
    private ArrayList<String> items;
    private int capacity;

    // Creator
    public BoundedStack(int capacity) {
        // โครงเปล่า เปรมทำส่วนนี้ต่อ
    }

    // Mutators
    public void push(String item) {
        // โครงเปล่า
    }

    public String pop() {
        return null; // คืนค่าหลอก
    }

    // Observers
    public int size() {
        return -1; // คืนค่าหลอก เพื่อให้เทสต์ FAIL ผ่าน
    }

    public boolean isEmpty() {
        return false; // คืนค่าหลอก
    }

    public boolean isFull() {
        return true; // คืนค่าหลอก
    }

    public String peek() {
        return null; // คืนค่าหลอก
    }

    // Producer
    public BoundedStack copy() {
        return null; // คืนค่าหลอก
    }
}
