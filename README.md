**BoundedStack (Stack ขนาดจำกัด) สำหรับจัดการข้อมูลประเภท String**

6821601178 นายปริวัฒน์ สุขวิจิตต์

6821601488 นายศุภกร รุ่งสุวรรณสกุล

/////    THAT'S PERFECT    /////


# BoundedStack (Abstract Data Type)

---

*   **โครงสร้างข้อมูลภายใน (Representation):** 
    *   **ทางเลือกที่เลือกใช้:** `ArrayList<String>`
    *   **ทางเลือกที่ปัดตก:** Native Array (`String[]`) และ `java.util.Stack`
    *   **เหตุผล:** การใช้ Native Array จำเป็นต้องจัดการล้างค่า (nullify) ข้อมูลที่ถูกดึงออกด้วยตนเองเพื่อป้องกันปัญหา Memory Leak ซึ่ง `ArrayList` สามารถจัดการกระบวนการนี้ได้ยืดหยุ่น
*   **การจัดการข้อผิดพลาด (Error Handling):** 
    *   **ทางเลือกที่เลือกใช้:** การโยน `Exception` (`IllegalArgumentException`, `IllegalStateException`) เพื่อให้ระบบหยุดทำงานทันที (Fail-fast) เมื่อมีการใช้งานผิดเงื่อนไข
    *   **ทางเลือกที่ปัดตก:** การคืนค่า `false` หรือ `null` (Fail-silent) และการใช้ `assert` ตรวจสอบอินพุตจากภายนอก
    *   **เหตุผล:** การทำ Fail-silent จะทำให้การติดตามข้อผิดพลาดในระบบขนาดใหญ่ทำได้ยาก และหลีกเลี่ยงการใช้ `assert` กับฝั่ง Client เนื่องจากระบบ Assertion สามารถถูกปิดการทำงาน (Disable) ได้ในระดับ Production จึงสงวน `assert` ไว้ใช้สำหรับการตรวจสอบ Representation Invariant ภายในเท่านั้น

---

## Abstraction Function และ Representation Invariant

*   **Abstraction Function (AF):** 
    `AF(items, capacity)` = Stack ขนาดจำกัดที่มีความจุสูงสุดเท่ากับ `capacity`
    *   `items.get(0)` คือข้อมูลที่อยู่ล่างสุดของ Stack (Bottom)
    *   `items.get(items.size() - 1)` คือข้อมูลที่อยู่บนสุดของ Stack (Top)
    *   ถ้า `items` เป็นลิสต์ว่าง หมายถึง Stack ว่างเปล่า
*   **Representation Invariant (RI):**
    *   `items != null`
    *   `capacity > 0`
    *   `items.size() <= capacity`
    *   `items` ต้องไม่บรรจุค่า `null` (`items.get(i) != null` ทุกตำแหน่ง)

---

## ข้อมูลจำเพาะของปฏิบัติการ (Operations Specification)

### หมวด Creator
**`BoundedStack(int capacity)`**
*   **หน้าที่:** สร้างสแตกว่างที่มีขนาดความจุสูงสุดตามที่กำหนด
*   **Precondition:** `capacity > 0`
*   **Postcondition:** ได้ออบเจ็กต์สแตกว่างที่มีขนาด `size() == 0`
*   **Exceptions:** โยน `IllegalArgumentException` หาก `capacity <= 0`

### หมวด Mutator
**`void push(String item)`**
*   **หน้าที่:** เพิ่มข้อมูลใหม่ลงบนจุดสูงสุดของสแตก
*   **Precondition:** สแตกต้องยังไม่เต็ม (`!isFull()`) และ `item != null`
*   **Postcondition:** ข้อมูล `item` จะถูกวางไว้ตำแหน่งบนสุดของสแตก และขนาด `size()` จะเพิ่มขึ้น 1
*   **Exceptions:** 
    *   โยน `IllegalStateException` หากสแตกเต็ม
    *   โยน `IllegalArgumentException` หาก `item` มีค่าเป็น `null`

**`String pop()`**
*   **หน้าที่:** ดึงและลบข้อมูลที่อยู่บนสุดของสแตกออก
*   **Precondition:** สแตกต้องไม่ว่าง (`!isEmpty()`)
*   **Postcondition:** ข้อมูลที่อยู่บนสุดจะถูกลบออก คืนค่าข้อมูลนั้นกลับไป และขนาด `size()` จะลดลง 1
*   **Exceptions:** โยน `IllegalStateException` หากสแตกว่างเปล่า

### หมวด Observer
**`int size()`**
*   **หน้าที่:** ตรวจสอบจำนวนข้อมูลปัจจุบันในสแตก
*   **Postcondition:** คืนค่าจำนวนข้อมูล (ตั้งแต่ 0 ถึง `capacity`) โดยสแตกไม่มีการเปลี่ยนแปลงสถานะ

**`boolean isEmpty()`**
*   **หน้าที่:** ตรวจสอบสถานะความว่างเปล่าของสแตก
*   **Postcondition:** คืนค่า `true` หากสแตกไม่มีข้อมูล หรือ `false` หากมีข้อมูลอย่างน้อย 1 ตัว โดยสแตกไม่มีการเปลี่ยนแปลงสถานะ

**`boolean isFull()`**
*   **หน้าที่:** ตรวจสอบสถานะการบรรจุของสแตกเทียบกับความจุสูงสุด
*   **Postcondition:** คืนค่า `true` หากจำนวนข้อมูลเท่ากับความจุสูงสุด หรือ `false` หากยังมีพื้นที่ว่าง โดยสแตกไม่มีการเปลี่ยนแปลงสถานะ

**`String peek()`**
*   **หน้าที่:** ตรวจสอบข้อมูลบนสุดของสแตกโดยไม่มีการลบออก
*   **Precondition:** สแตกต้องไม่ว่าง (`!isEmpty()`)
*   **Postcondition:** คืนค่าข้อมูลตำแหน่งบนสุด โดยสถานะและขนาดของสแตกไม่มีการเปลี่ยนแปลง
*   **Exceptions:** โยน `IllegalStateException` หากสแตกว่างเปล่า

### หมวด Producer
**`BoundedStack copy()`**
*   **หน้าที่:** สร้างสแตกสำเนา (Clone) ที่มีขนาดความจุและลำดับข้อมูลตรงกับสแตกต้นฉบับ
*   **Postcondition:** คืนค่าออบเจ็กต์ `BoundedStack` ตัวใหม่ที่เป็นอิสระจากสแตกเดิม การแก้ไขข้อมูลในสแตกตัวใหม่จะไม่ส่งผลกระทบ (Side-effect) ใดๆ ต่อสแตกต้นฉบับ

---

## การคอมไพล์และการทดสอบระบบ (Compilation & Testing)

ระบบมาพร้อมกับ `TestRunner` สำหรับทดสอบความถูกต้องของการทำงานโดยอัตโนมัติ การรันโปรแกรมทดสอบจำเป็นต้องเปิดใช้งานแฟล็ก `-ea` (Enable Assertions) เพื่อให้กลไกตรวจสอบ Representation Invariant (`checkRep`) ทำงานได้อย่างสมบูรณ์

```bash
# 1. คอมไพล์ซอร์สโค้ด
javac BoundedStack.java TestRunner.java

# 2. รันโปรแกรมทดสอบพร้อมเปิดใช้งานระบบ Assertion
java -ea TestRunner
