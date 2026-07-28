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
        
        System.out.println("==================================");
        System.out.printf("PASS %d / FAIL %d%n", pass, fail);
        if (fail > 0) System.exit(1);
    }

    // ---------------------------------------------------------
    // 1. หมวดเทสต์ Creator (ทดสอบการสร้าง)
    // ---------------------------------------------------------
    static void testCreator() {
        System.out.println("\n-- testCreator --");
        
        // เคสปกติ: ความจุเป็นบวก
        BoundedStack s = new BoundedStack(5);
        check("new(5) -> size is 0", s.size() == 0);
        check("new(5) -> isEmpty is true", s.isEmpty());

        // เคสขอบเขต (Boundary): ความจุเป็น 0 ต้องพัง
        boolean threwZero = false;
        try { new BoundedStack(0); }
        catch (IllegalArgumentException e) { threwZero = true; }
        check("new(0) -> throws IllegalArgumentException", threwZero);

    }

    // ---------------------------------------------------------
    // 2. หมวดเทสต์ Mutator: Push (ทดสอบการใส่)
    // ---------------------------------------------------------
    static void testPush() {
        System.out.println("\n-- testPush --");
        
        BoundedStack s = new BoundedStack(2);
        
        // เคสปกติ
        s.push("A");
        check("push(A) -> size is 1", s.size() == 1);
        check("push(A) -> peek is A", s.peek().equals("A"));

        // เคสขอบเขต: ดันจนเต็มพอดี
        s.push("B");
        check("push(B) -> isFull is true", s.isFull());

        // เคสขอบเขต: เต็มแล้วยังฝืน Push ต้องพัง (Exception)
        boolean threwFull = false;
        try { s.push("C"); }
        catch (IllegalStateException e) { threwFull = true; }
        check("push when full -> throws IllegalStateException", threwFull);

    }

    // ---------------------------------------------------------
    // 3. หมวดเทสต์ Mutator: Pop (ทดสอบการดึง)
    // ---------------------------------------------------------
    static void testPop() {
        System.out.println("\n-- testPop --");
        
        BoundedStack s = new BoundedStack(3);
        s.push("X"); s.push("Y");
        
        // เคสปกติ: หยิบตัวบนสุดออก
        String top = s.pop();
        check("pop() -> returns Y", top.equals("Y"));
        check("pop() -> size decreases to 1", s.size() == 1);

        // เคสขอบเขต: หยิบจนเกลี้ยง
        s.pop();
        check("pop() till empty -> isEmpty is true", s.isEmpty());

        // เคสขอบเขต: ว่างแล้วยังฝืน Pop ต้องพัง (Exception)
        boolean threwEmpty = false;
        try { s.pop(); } 
        catch (IllegalStateException e) { threwEmpty = true; }
        check("pop when empty -> throws IllegalStateException", threwEmpty);
    }

}



