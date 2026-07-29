public class TestRunner {
    static int pass = 0, fail = 0;

    static void check(String name, boolean ok) {
        if (ok) { pass++; System.out.println("  [PASS] " + name); }
        else    { fail++; System.out.println("  [FAIL] " + name); }
    }

    public static void main(String[] args) {
        boolean ea = false;
        assert ea = true;
        if (!ea) System.out.println("** คำเตือน: เปิด -ea ด้วยตอนรัน **");

        System.out.println("=== BoundedStack Test Suite ===");

        testCreator();
        testPush();
        testPop();
        testObservers();
        testProducer();
        
        System.out.println("==================================");
        System.out.printf("PASS %d / FAIL %d%n", pass, fail);
        if (fail > 0) System.exit(1);
    }

    // ---------------------------------------------------------
    // 1. หมวดเทสต์ Creator (ทดสอบการสร้าง)
    // ---------------------------------------------------------
    static void testCreator() {
        System.out.println("\n-- testCreator --");
        final int DEFAULT_CAPACITY = 5;
        
        // เคสปกติ: ความจุเป็นบวก
        BoundedStack stack = new BoundedStack(DEFAULT_CAPACITY);
        check("new(5) -> size is 0", stack.size() == 0);
        check("new(5) -> isEmpty is true", stack.isEmpty());

        // เคสขอบเขต (Boundary): ความจุเป็น 0
        boolean threwZero = false;
        try { new BoundedStack(0); }
        catch (IllegalArgumentException e) { threwZero = true; }
        check("new(0) -> throws IllegalArgumentException", threwZero);

        // เคสขอบเขต: ความจุ 1 (Boundary)
        BoundedStack stackSizeOne = new BoundedStack(1);
        check("new(1) -> size is 0", stackSizeOne.size() == 0);
        check("new(1) -> isFull is false", !stackSizeOne.isFull());

        // เคสขอบเขต: ความจุติดลบ
        boolean threwNegative = false;
        try { new BoundedStack(-1); }
        catch (IllegalArgumentException e) { threwNegative = true; }
        check("new(-1) -> throws IllegalArgumentException", threwNegative);

        // เคส กล่องที่เพิ่งสร้างใหม่ ต้องยังไม่เต็มแน่ๆ
        check("new(5) -> isFull is false", !stack.isFull());

    }

    // ---------------------------------------------------------
    // 2. หมวดเทสต์ Mutator: Push (ทดสอบการใส่)
    // ---------------------------------------------------------
    static void testPush() {
        System.out.println("\n-- testPush --");
        
        BoundedStack stack = new BoundedStack(2);
        
        // เคสปกติ
        stack.push("A");
        check("push(A) -> size is 1", stack.size() == 1);
        check("push(A) -> peek is A", stack.peek().equals("A"));

        // เคสขอบเขต: ดันจนเต็มพอดี
        stack.push("B");
        check("push(B) -> isFull is true", stack.isFull());

        // เคสขอบเขต: เต็มแล้วยังฝืน Push ต้องพัง (Exception)
        boolean threwFull = false;
        try { stack.push("C"); }
        catch (IllegalStateException e) { threwFull = true; }
        check("push when full -> throws IllegalStateException", threwFull);

        // เคสรับมือ input ผิดปกติ: push(null) ต้องพัง
        boolean threwNull = false;
        try { stack.push(null); }
        catch (IllegalArgumentException e) { threwNull = true; }
        check("push(null) -> throws IllegalArgumentException", threwNull);

        // เคส ลองใส่ของหน้าตาเหมือนเดิมซ้ำลงไป (ใส่ "A" ซ้ำ)
        BoundedStack duplicateStack = new BoundedStack(3);
        duplicateStack.push("A");
        duplicateStack.push("A");
        check("push duplicate items -> size is 2", duplicateStack.size() == 2);

    }

    // ---------------------------------------------------------
    // 3. หมวดเทสต์ Mutator: Pop (ทดสอบการดึง)
    // ---------------------------------------------------------
    static void testPop() {
        System.out.println("\n-- testPop --");
        
        BoundedStack stack = new BoundedStack(3);
        stack.push("X"); 
        stack.push("Y");
        
        // เคสปกติ: หยิบตัวบนสุดออก
        String top = stack.pop();
        check("pop() -> returns Y", top.equals("Y"));
        check("pop() -> size decreases to 1", stack.size() == 1);

        // เคสขอบเขต: หยิบจนเกลี้ยง
        stack.pop();
        check("pop() till empty -> isEmpty is true", stack.isEmpty());

        // เคสขอบเขต: ว่างแล้วยังฝืน Pop ต้องพัง (Exception)
        boolean threwEmpty = false;
        try { stack.pop(); } 
        catch (IllegalStateException e) { threwEmpty = true; }
        check("pop when empty -> throws IllegalStateException", threwEmpty);
    }

    // ---------------------------------------------------------
    // 4. หมวดเทสต์ Observers (ต้องไม่มี Side Effect)
    // ---------------------------------------------------------
    static void testObservers() {
        System.out.println("\n-- testObservers --");
        
        BoundedStack stack = new BoundedStack(5);
        stack.push("Test");
        int sizeBefore = stack.size();
        
        // เรียก Observer รัวๆ
        stack.isEmpty();
        stack.isFull();
        stack.peek();

        // เคสขอบเขต: peek สแตกว่างต้องโยน Exception
        BoundedStack emptyStack = new BoundedStack(1);
        boolean threwPeekEmpty = false;
        try { emptyStack.peek(); }
        catch (IllegalStateException e) { threwPeekEmpty = true; }
        check("peek when empty -> throws IllegalStateException", threwPeekEmpty);
        
        // ตรวจสอบว่าสแตกไม่ได้ถูกแอบเปลี่ยนแปลง
        check("observers have no side effects", stack.size() == sizeBefore);

        // เคสที่ ลองเรียก peek() ดูว่าของยังอยู่ครบไหม
        stack.peek();
        check("peek() doesn't remove item -> size is still 1", stack.size() == 1);
    }

    // ---------------------------------------------------------
    // 5. หมวดเทสต์ Producer (สร้างใหม่ไม่กวนตัวเก่า)
    // ---------------------------------------------------------
    static void testProducer() {
        System.out.println("\n-- testProducer --");
        
        BoundedStack original = new BoundedStack(3);
        original.push("One");
        
        // สร้างตัวโคลน
        BoundedStack clone = original.copy();
        
        check("copy() -> size matches original", clone.size() == original.size());
        
        // ลองแก้ตัวโคลน ต้องไม่กระทบตัวจริง
        clone.push("Two");
        check("mutating clone does not affect original", original.size() == 1);
    }
}