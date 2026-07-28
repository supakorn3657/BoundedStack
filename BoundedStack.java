import java.util.ArrayList;

public class BoundedStack {
    
    /*      //////  6821601178 ปริวัฒน์ สุขวิจิตต์  //////
     * Abstraction Function (AF):
     *   AF(items, capacity) = Stack ขนาดจำกัดที่มีความจุสูงสุดเท่ากับ 'capacity'
     *   - items.get(0) คือข้อมูลที่อยู่ล่างสุดของ Stack (Bottom)
     *   - items.get(items.size() - 1) คือข้อมูลที่อยู่บนสุดของ Stack (Top)
     *   - ถ้า items เป็นลิสต์ว่าง หมายถึง Stack ว่างเปล่า
     *
     * Representation Invariant (RI):
     *   - items != null
     *   - capacity > 0
     *   - items.size() <= capacity
     *   - items ต้องไม่บรรจุค่า null (items.get(i) != null ทุกตำแหน่ง)
     */
    private ArrayList<String> items;
    private int capacity;

    /**
     * Helper method ตรวจสอบ Rep Invariant
     */
    private void checkRep() {
        assert items != null : "items ต้องไม่เป็น null";
        assert capacity > 0 : "capacity ต้องเป็นจำนวนเต็มบวก";
        assert items.size() <= capacity : "จำนวนข้อมูลเกิน capacity";
        for (String item : items) {
            assert item != null : "ห้ามบรรจุค่า null ใน items";
        }
    }

    /**
     * สร้างสแตกใหม่ตามขนาดความจุสูงสุดที่กำหนด
     * 
     * @param capacity ความจุสูงสุดของสแตก (ต้องมากกว่า 0)
     * @pre capacity > 0
     * @post ได้สแตกว่างที่มีขนาด size เป็น 0
     * @throws IllegalArgumentException เมื่อ capacity <= 0
     */
    // Creator
    public BoundedStack(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity ต้องมากกว่า 0");
        }
        this.capacity = capacity;
        this.items = new ArrayList<>();
        checkRep();
    }

    /**
    * นำข้อมูล item ใส่ลงไปในตำแหน่งบนสุดของสแตก
    * @param item ข้อความที่ต้องการใส่ลงในสแตก (ต้องไม่เป็น null)
    * @pre สแตกต้องยังไม่เต็ม (size < capacity) และ item != null
    * @post ข้อมูล item จะอยู่บนสุดของสแตก และ size ของสแตกจะเพิ่มขึ้น 1
    * @throws IllegalStateException เมื่อสแตกเต็มแล้ว
    * @throws IllegalArgumentException เมื่อ item เป็น null
    */
    
    // Mutators //
    public void push(String item) {
        if (isFull()) {
            throw new IllegalStateException("Stack เต็มแล้ว");}
        items.add(item);
        checkRep();
    }

    /**
     * ดึงข้อมูลที่อยู่บนสุดของสแตกออกมาและคืนค่าข้อมูลนั้น
     * @return ข้อความตที่อยู่บนสุดของสแตกออกและคืนค่าข้อมูลนั้น
     * @pre สแตกต้องไม่ว่าง (!isEmpty())
     * @post ข้อมูลบนสุดของสแตกจะถูกลบออก และ size ของสแตกจะลดลง 1
     * @throws IllegalStateException เมื่อสแตกว่างเปล่า ไม่สามารถ pop ได้
     */
    public String pop() {
        if(isEmpty()) {
            throw new IllegalStateException("Stack ว่างเปล่า");
        }
        String topItem = items.remove(items.size() - 1);
        checkRep();
        return topItem;
    }

    // Observers
    public int size() {
        return items.size(); // ปรับคืนค่าจริง เพื่อให้ testCreator() ของเพื่อนผ่าน PASS
    }

    public boolean isEmpty() {
        return items.isEmpty(); // ปรับคืนค่าจริง เพื่อให้ testCreator() ของเพื่อนผ่าน PASS
    }

    public boolean isFull() {
        return items.size() == capacity;
    }

    public String peek() {
        return items.get(items.size() - 1);
    }

    // Producer
    public BoundedStack copy() {
        return null; // คืนค่าหลอก
    }
}