        /////   นายศุภกร รุ่งสุวรรณสกุล 6821601488   /////

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
    // TODO: เปรมเขียนต่อ เคสขอบเขต: ความจุติดลบ (-1) ต้องพัง
}



