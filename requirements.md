lab 1 Java OOP Practice - Система управления магазином 🛒
Описание 📋 задачи
Создайте простую систему управления магазином, используя концепции объектно-ориентированного программирования на Java. Система должна включать классы Product и Category, которые работают вместе для управления запасами. 🏷️

Структура 🗂️ проекта
src/ ├── product/ │ ├── Product.java # Класс товара с управлением 🏪 запасами │ └── ShopDemo.java # Демонстрация функциональности 🎉 продукта └── категория/ └── Category.java # Класс категории для организации товаров 📦

Обзор 📚 классов
Класс продукта (product. Продукт) 🏷️
Назначение: Представляет отдельные товары в магазине 🛍️

Ключевые особенности:

Атрибуты товара: ID, название, описание, цена, количество, категория, состояние 💰 на складе
Конструкторы: Стандартные и параметризованные 🛠️
Управление запасами: добавление остатков, продажа товаров 📈
Бизнес-операции: применение скидок, расчет общей стоимости 💵
Проверка данных по цене и количеству ✅
Основные методы:

addStock(int amount) - Добавить запасы ➕
sellProduct(int amount) - Продавать товары с проверкой 🛒
applyDiscount(double percent) - Применить процентную скидку 💸
calculateTotalValue() - Рассчитать стоимость 📊 запасов
displayProductInfo() - Показать подробную информацию о продукте 📖
Категория Класс (категория. Категория) 📦
Цель: Упорядочивает продукты по категориям 📂

Ключевые особенности:

Атрибуты категории: ID, имя, описание 🏷️
Управление 📦 коллекцией товаров
Расчеты 📊 на уровне категорий
Основные методы:

addProduct(Продукт продукта) - Добавить товар в категорию ➕
removeProduct(Продукт продукта) - Удалить продукт из категории ➖
getTotalValue() - Рассчитать общую стоимость всех товаров в категории 💰
displayCategoryInfo() - Показать категорию и ее продукты 📋

lab 2
Practice 2 — Encapsulation & Validation Guard (Tech Requirement)

Theme: Keep your objects in a valid state. Builds on: Task 1 (Product, Category, ShopDemo). Rule of thumb: No invalid data gets inside Product.

---

🎯 Goal

Refactor product.Product (and a tiny bit of category.Category) to enforce encapsulation and add simple validation guards. No exceptions yet, no complex patterns. Just private fields + guarded updates that return boolean.

---

✅ What to change (minimal & easy)

1) Encapsulation

· All fields in Product must be private.

· Expose state via getters only.

· Replace plain setters with guarded mutators that return boolean and do nothing on invalid input.

2) Validation rules (keep it simple)

· id: not null, trimmed length >= 2.

· name: not null, trimmed length >= 2.

· description: can be null or trimmed; if not null, length <= 200.

· price: >= 0.0 and <= 1_000_000.0.

· quantity: >= 0 and <= 1_000_000.

· category: not null.

· Discounts: percent must be 0..90. If outside → ignore and return original price in preview.

3) Guarded mutators (proposed signatures)

// inside product.Product

public boolean trySetId(String id);

public boolean trySetName(String name);

public boolean trySetDescription(String description);

public boolean trySetPrice(double price);

public boolean trySetQuantity(int quantity);

public boolean trySetCategory(category.Category category);

· Return true only if the field is updated.

· On invalid input: return false and do not change the field.

4) Use guards in inventory methods

· addStock(int amount): accept only amount > 0; update quantity; return true/false.

· sellProduct(int amount): only if amount > 0 and amount <= quantity; update quantity; return true/false.

· applyDiscount(double percent): if percent not in 0..90, do nothing and return false; if valid, apply new price and return true.

o (If your Task 1 had a “preview” method instead of mutation, keep that; just enforce the 0..90 rule.)

· calculateTotalValue(): unchanged (but now it relies on guarded values).

5) Computed stock status (no direct setter)

· Remove any public setter for “stock status”.

· Provide a derived read-only method:

public String getStockStatus(); // returns "OUT_OF_STOCK" (0), "LOW" (1..10), or "IN_STOCK" (>10)

· Thresholds: 0 → OUT_OF_STOCK, 1..10 → LOW, >10 → IN_STOCK.

6) Category safety (tiny change)

· In category.Category, make addProduct(Product p) return boolean:

o Reject null.

o Reject duplicates of the same object reference (simple contains check).

· removeProduct(Product p) can stay as in Task 1 (or return boolean if you prefer).

---

🧪 Demo requirements (product.ShopDemo)

Update your demo to prove guards work:

1. Create a Category (e.g., “Stationery”) and a Product (e.g., Pen).

2. Accepted updates:

o trySetPrice(250.0) → true

o addStock(20) when quantity was, say, 5 → true → quantity becomes 25

3. Rejected updates:

o trySetPrice(-1.0) → false (price unchanged)

o trySetName("A") → false (name unchanged)

o sellProduct(10_000) if quantity is lower → false (quantity unchanged)

o applyDiscount(200) → false (ignored)

4. Show getStockStatus() before/after restocks/sales.

5. Add the product to the category twice; the second add must be rejected.

Print each action and the resulting state so it’s obvious what passed/failed.

---

✅ Acceptance Criteria

· All Product fields are private; external code cannot mutate them directly.

· Guarded mutators above exist and preserve old values on invalid input.

· Inventory methods (addStock, sellProduct, applyDiscount) validate inputs and return boolean.

· getStockStatus() is computed from quantity (no direct setter).

· Category.addProduct rejects null and duplicates (by reference).

· ShopDemo prints at least 2 accepted and 3 rejected actions.

---

📊 Suggested Grading ( /10 )

· Encapsulation (private fields + getters) — 2

· Guarded mutators work (return boolean, no state corruption) — 4

· Inventory methods validate correctly — 2

· Demo proves both accepted/rejected flows + stock status — 2

lab 3
Practice 3 — Constructors & Static Factory (Tech Requirement)

Builds on: Task 1 (basic Product) & Task 2 (encapsulation + guards). Theme: Learn constructor overloading, constructor chaining, default initialization, and simple static members / static factory methods.


Goal

Enhance product.Product to support multiple constructors and static factory helpers, while keeping validation simple and consistent with Task 2.


What to add (easy & minimal)

A) Constructors (overloaded + chained)

Implement three constructors for Product:

1. No-args constructor — sets safe defaults:

o id = "AUTO-" + nextSeq()

o name = "Unnamed"

o description = null

o price = 0.0

o quantity = 0

o category = null (can be set later)

2. Required-args constructor — id, name, price:

public Product(String id, String name, double price)

o Sets quantity = 0, description = null, category = null.

3. Full-args constructor — id, name, description, price, quantity, category.

Rules:

· Use constructor chaining with this(...) to avoid code duplication.

· Inside constructors, reuse your guards from Task 2: call trySetX methods. If a guard returns false, keep the current value (default from the chained constructor).

B) Static members

Add these static members to Product:

· public static final String DEFAULT_CURRENCY = "KZT";

· private static int SEQ = 1; — simple product id sequence.

· public static int getCreatedCount() — how many Product objects were created (increment in each constructor).

Helper:

private static String nextSeq() { return String.valueOf(SEQ++); }

C) Static factory methods (friendly names)

Add two static factory methods:

1. public static Product of(String id, String name, double price)

o Equivalent to the required-args constructor.

2. public static Product freeSample(String name)

o Creates a product with:

§ auto id (AUTO-...),

§ given name,

§ price = 0.0,

§ quantity = 1.

Tip: Static factories can have good names and can return preconfigured instances.


Demo (product.ShopDemo or product.ShopDemo3)

Print results to show all flows:

1. Create products via each constructor + the two static factories.

2. Show automatically assigned ids (AUTO-1, AUTO-2, ...).

3. Prove guards still work when constructor parameters are invalid (e.g., new Product("", "A", -5) should keep safe defaults for those fields).

4. Print Product.getCreatedCount() at the end.

Example usage (you can copy into your demo):

Product p1 = new Product(); // no-args

Product p2 = new Product("P100", "Notebook", 950.0); // required-args

Product p3 = new Product("P200", "Headphones", "BT 5.0", 14990.0, 5, electronics); // full-args


Product p4 = Product.of("P300", "Pencil", 120.0); // static factory

Product p5 = Product.freeSample("Sticker"); // static factory


System.out.println(p1);

System.out.println(p2);

System.out.println(p3);

System.out.println(p4);

System.out.println(p5);


System.out.println("Created count = " + Product.getCreatedCount());

lab 4
Practice 4 — Why Product Subtypes Beat Other Inheritance Options

Builds on: Task 1–3 (Product with encapsulation, constructors, static factory). Scope: Analysis of inheritance design choices in e-commerce systems.

---

🎯 Objective

Analyze why Product subtypes (Physical/Digital) is the best inheritance choice compared to other common e-commerce hierarchies:

1. ShippingOption hierarchy — needs logistics context

2. PaymentMethod hierarchy — needs Orders/Checkout system

3. Review hierarchy — isn't great for inheritance

4. Product subtypes — you already implemented (Physical/Digital) ✅

---

🔍 Analysis: Why Product Subtypes Win

❌ Why Other Options Don't Work Well

1. ShippingOption Hierarchy

// PROBLEMATIC: Too much logistics context needed

abstract class ShippingOption {

abstract double calculateCost(Address from, Address to, Package package);

abstract int estimateDays(Address from, Address to);

abstract boolean isAvailable(Address from, Address to);

}

class StandardShipping extends ShippingOption { ... }

class ExpressShipping extends ShippingOption { ... }

class OvernightShipping extends ShippingOption { ... }

Issues:

· Requires complex logistics system (Address, Package, routing)

· Needs external APIs for real-time shipping rates

· Too much business logic outside core product domain

· Hard to test without full shipping infrastructure

2. PaymentMethod Hierarchy

// PROBLEMATIC: Needs complete checkout system

abstract class PaymentMethod {

abstract boolean processPayment(double amount, String currency);

abstract String getTransactionId();

abstract boolean refund(String transactionId, double amount);

}

class CreditCard extends PaymentMethod { ... }

class PayPal extends PaymentMethod { ... }

class BankTransfer extends PaymentMethod { ... }

Issues:

· Requires Orders, Checkout, Transaction entities

· Needs payment gateway integrations

· Security concerns (PCI compliance)

· Complex state management across payment flow

3. Review Hierarchy

// PROBLEMATIC: Not good inheritance candidate

abstract class Review {

String content;

int rating;

Date createdAt;

}

class ProductReview extends Review { ... }

class SellerReview extends Review { ... }

class ServiceReview extends Review { ... }

Issues:

· Very similar behavior across subclasses

· Better suited for composition (Review + Reviewable interface)

· Rating logic is identical across types

· Content validation is the same

✅ Why Product Subtypes Are Perfect

4. Product Subtypes (Physical/Digital) - IMPLEMENTED ✅

// EXCELLENT: Clear domain boundaries, distinct behaviors

public class PhysicalProduct extends Product {

private double weightKg;

private double lengthCm, widthCm, heightCm;


public double estimateShippingCost() {

// Physical-specific: volumetric calculation

double volumetric = (lengthCm * widthCm * heightCm) / 5000.0;

return Math.max(weightKg, volumetric) * 100;

}

}


public class DigitalProduct extends Product {

private double downloadSizeMb;

private String licenseKey;


public boolean isLicenseRequired() {

// Digital-specific: license validation

return licenseKey != null && !licenseKey.isBlank();

}

}

Why This Works Perfectly:

· ✅ Clear domain boundaries — each type has distinct attributes

· ✅ Different business logic — shipping vs licensing

· ✅ Self-contained — no external dependencies

· ✅ Easy to test — simple validation rules

· ✅ Extensible — easy to add new product types

· ✅ Real-world relevance — matches actual e-commerce needs

---

📋 Implementation Example (Already Done)

Project Layout

src/

└── product/

├── Product.java # from Task 2–3 (with guards and constructors)

├── PhysicalProduct.java # Product subclass

└── DigitalProduct.java # Product subclass

1) PhysicalProduct extends Product

Private fields:

· double weightKg (0..1000)

· double lengthCm, widthCm, heightCm (each 0..1000)

Methods:

· boolean trySetWeightKg(double v)

· boolean trySetDimensions(double l, double w, double h) — all valid, otherwise false

· double estimateShippingCost() Formula: volumetric = (l * w * h) / 5000.0 → billable = max(weightKg, volumetric) → cost = billable * 100 (KZT)

Constructors (chaining required):

· PhysicalProduct() — safe defaults via super()

· PhysicalProduct(String id, String name, double price, double weightKg)

· PhysicalProduct(String id, String name, String description, double price, int quantity, double weightKg, double l, double w, double h)

Inside constructors use trySet... from Product; invalid values are ignored (defaults are preserved).

2) DigitalProduct extends Product

Private fields:

· double downloadSizeMb (0..1_000_000)

· String licenseKey (nullable, length ≤ 64)

Methods:

· boolean trySetDownloadSizeMb(double v)

· boolean trySetLicenseKey(String key)

· boolean isLicenseRequired() → licenseKey != null && !licenseKey.isBlank()

Constructors:

· DigitalProduct()

· DigitalProduct(String id, String name, double price, double downloadSizeMb)

· DigitalProduct(String id, String name, String description, double price, int quantity, double downloadSizeMb, String licenseKey)

3) Printing

Override toString() in each subclass: add subclass info to super.toString().

---

🧪 Demo (product.ShopDemo4)

Show at minimum:

1. Creation using three types of constructors for each subclass (one example each).

2. Valid and invalid updates:

laptop.trySetDimensions(-1, 10, 10) // false

ebook.trySetDownloadSizeMb(2048) // true

3. Subclass method calls:

laptop.estimateShippingCost();

ebook.isLicenseRequired();

4. Printing via System.out.println(...) — shows toString() of base class and subclass additions.

lab 5
Practice 5 — Polymorphism: Price Policies + Method Overloading/Overriding

Builds on: Task 1–3 (Product with encapsulation/constructors/static), Task 4 (PhysicalProduct, DigitalProduct). Scope: Keep the project minimal: Products only (no Orders/Payments).


🎯 Goal

Demonstrate polymorphism with a unified price rules interface, plus overloading (compile-time polymorphism) and overriding (runtime polymorphism) on Product subclasses.

You will:

1. Introduce PricePolicy (an interface for pricing rules).

2. Implement three rules: PercentageOff, FixedOff, BogoHalf (buy-one-get-one-half).

3. Overload finalPrice(...) methods in Product.

4. Override price calculation in PhysicalProduct and DigitalProduct.

5. Use a polymorphic list (List<Product>, List<PricePolicy>) in a demo to show different behavior per subtype.


📁 Project Structure

src/

└── product/

├── Product.java

├── PhysicalProduct.java

├── DigitalProduct.java

├── pricing/

│ ├── PricePolicy.java

│ ├── PercentageOff.java

│ ├── FixedOff.java

│ └── BogoHalf.java

└── ShopDemo5.java


1) Interface: PricePolicy

// product/pricing/PricePolicy.java

package product.pricing;


import product.Product;


public interface PricePolicy {

/** Human-readable rule name (for printing). */

String name();


/**

* Calculate the FINAL cost for 'qty' units of 'p' (NO shipping here).

* Must NOT mutate Product.

*/

double apply(Product p, int qty);


/** By default, applicable to all products. */

default boolean applicableTo(Product p) { return true; }

}

Implementations

// product/pricing/PercentageOff.java

package product.pricing;

import product.Product;


public class PercentageOff implements PricePolicy {

private final double percent; // 0..90


public PercentageOff(double percent) {

this.percent = Math.max(0, Math.min(90, percent));

}


@Override public String name() { return "Percent-" + percent + "%"; }


@Override public double apply(Product p, int qty) {

double unit = p.getPrice() * (1 - percent / 100.0);

return unit * Math.max(0, qty);

}

}

// product/pricing/FixedOff.java

package product.pricing;

import product.Product;


public class FixedOff implements PricePolicy {

private final double amount; // >= 0


public FixedOff(double amount) { this.amount = Math.max(0, amount); }


@Override public String name() { return "Fixed-" + amount; }


@Override public double apply(Product p, int qty) {

double unit = Math.max(0.0, p.getPrice() - amount);

return unit * Math.max(0, qty);

}

}

// product/pricing/BogoHalf.java

// For every pair: second item is -50%.

// Price for a pair = 1.5 * price; average = 0.75 * price per unit.

package product.pricing;

import product.Product;


public class BogoHalf implements PricePolicy {

@Override public String name() { return "BOGO-HALF"; }


@Override public double apply(Product p, int qty) {

double price = p.getPrice();

int pairs = Math.max(0, qty) / 2;

int singles = Math.max(0, qty) % 2;

return pairs * (price * 1.5) + singles * price;

}

}


2) Product: Method Overloading

Add overloads to Product (keep your existing fields/guards from previous tasks):

// Product.java — add these overloads


// 1) Single unit, no rules

public double finalPrice() {

return getPrice();

}


// 2) qty units, no rules

public double finalPrice(int qty) {

if (qty <= 0) return 0.0;

return getPrice() * qty;

}


// 3) qty + single rule (no shipping here; subclasses may add it)

public double finalPrice(int qty, product.pricing.PricePolicy policy) {

if (qty <= 0) return 0.0;

if (policy == null || !policy.applicableTo(this)) return finalPrice(qty);

return policy.apply(this, qty);

}


// 4) qty + list of rules (choose the best = minimal price)

public double finalPrice(int qty, java.util.List<product.pricing.PricePolicy> policies) {

if (qty <= 0) return 0.0;

if (policies == null || policies.isEmpty()) return finalPrice(qty);

double best = Double.POSITIVE_INFINITY;

for (var pp : policies) {

double v = finalPrice(qty, pp);

if (v < best) best = v;

}

return best;

}


3) Subclasses: Method Overriding

PhysicalProduct

Add shipping once per order (after discounts). Reuse your estimateShippingCost() from Task 4.

// PhysicalProduct.java — override single-rule version

@Override

public double finalPrice(int qty, product.pricing.PricePolicy policy) {

double base = super.finalPrice(qty, policy); // rule applied

return base + estimateShippingCost(); // add shipping once

}


// (optional) also override the List<PricePolicy> version to add shipping to the chosen best:

@Override

public double finalPrice(int qty, java.util.List<product.pricing.PricePolicy> policies) {

double base = super.finalPrice(qty, policies);

return base + estimateShippingCost();

}

DigitalProduct

Ignore BOGO-half (digital items don’t participate). Two options:

· (Simple) Handle in DigitalProduct.finalPrice(...).

· (Clean) Override applicableTo in BogoHalf to return false for DigitalProduct (optional).

Simple option:

// DigitalProduct.java

@Override

public double finalPrice(int qty, product.pricing.PricePolicy policy) {

if (policy instanceof product.pricing.BogoHalf) {

return super.finalPrice(qty); // ignore BOGO-half for digital

}

return super.finalPrice(qty, policy);

}


4) Demo — ShopDemo5 (Polymorphic Lists)

// product/ShopDemo5.java

package product;


import product.pricing.*;

import java.util.List;


public class ShopDemo5 {

public static void main(String[] args) {

PhysicalProduct laptop = new PhysicalProduct("P-LAP-1","Laptop",450_000.0,1.8);

laptop.trySetDimensions(35,24,2);


DigitalProduct ebook = new DigitalProduct("P-EBK-1","E-Book",1_500.0,12.5);


List<Product> items = List.of(laptop, ebook);

List<PricePolicy> rules = List.of(new PercentageOff(10), new FixedOff(50), new BogoHalf());


for (Product p : items) {

for (int qty : new int[]{1, 2}) {

System.out.println("\n== " + p.getName() + " | qty=" + qty);

System.out.println("Base: " + p.finalPrice(qty));


for (PricePolicy r : rules) {

System.out.println(r.name() + ": " + p.finalPrice(qty, r));

}

System.out.println("Best(of all): " + p.finalPrice(qty, rules));

}

}

}

}

Expected observations

· PhysicalProduct adds shipping on top of any rule.

· DigitalProduct ignores BogoHalf but accepts other rules.

· Overloading chooses method by signature; overriding resolves by runtime type.

lab 6
Abstract Classes & Interfaces: Promotions (Template Method) + Tax & Shipping Interfaces

Builds on: Task 1–5 (Product, PhysicalProduct, DigitalProduct, price rules). Scope: Keep the domain minimal (Products only). We will add one abstract class for promotions and two small interfaces for cross-cutting behavior.

1. Refactor pricing rules to showcase an abstract class with a Template Method.

2. Add interfaces for tax and shipping that different product types can implement/use.

You will:

· Create abstract class Promotion that implements PricePolicy and factors shared logic.

· Implement three concrete promotions by extending Promotion.

· Add interfaces TaxPolicy and Shippable and wire them into price calculation.

· Demonstrate polymorphism with lists of Promotion and different TaxPolicy on PhysicalProduct vs DigitalProduct.

📁 Project Structure

src/

└── product/

├── Product.java

├── PhysicalProduct.java

├── DigitalProduct.java

├── pricing/

│ ├── PricePolicy.java # from Task 5 (keep it)

│ ├── Promotion.java # NEW (abstract class)

│ ├── PercentagePromotion.java # extends Promotion

│ ├── FixedPromotion.java # extends Promotion

│ └── BogoHalfPromotion.java # extends Promotion (pair logic)

├── tax/

│ ├── TaxPolicy.java # NEW (interface)

│ ├── NoTax.java # implements TaxPolicy

│ ├── FlatVat.java # implements TaxPolicy (e.g., 12%)

│ └── ReducedDigitalVat.java # implements TaxPolicy (e.g., 5% only for DigitalProduct)

├── shipping/

│ └── Shippable.java # NEW (interface)

└── ShopDemo6.java

lab 7
Практическое задание: Анализ и расширение проекта согласно принципам SOLID (SRP и OCP)

Тема:

Применение принципов Single Responsibility и Open/Closed в проектировании модульной системы ценообразования (Promotions, Tax Policies, Shipping).


Цель:

На примере существующего Java-проекта изучить, как корректно применять два базовых принципа SOLID:

· S — Single Responsibility Principle (SRP)

· O — Open/Closed Principle (OCP)

А также закрепить навыки правильного разделения ответственности, проектирования стратегий (Strategy), шаблонных методов (Template Method), применения интерфейсов и наследования.


📁 Исходный проект

Студенту предоставляется Java-проект следующей структуры:

src/

└── product/

├── Product.java

├── PhysicalProduct.java

├── DigitalProduct.java

├── pricing/

│ ├── PricePolicy.java

│ ├── Promotion.java

│ ├── PercentagePromotion.java

│ ├── FixedPromotion.java

│ └── BogoHalfPromotion.java

├── tax/

│ ├── TaxPolicy.java

│ ├── NoTax.java

│ ├── FlatVat.java

│ └── ReducedDigitalVat.java

├── shipping/

│ └── Shippable.java

└── ShopDemo6.java


Задание (теоретико-практическое)

Часть 1 — Анализ соответствия SRP и OCP (обязательная)

1.1. Анализ SRP

Для каждого из следующих классов:

· Product

· PhysicalProduct

· DigitalProduct

· Promotion

· PercentagePromotion

· FixedPromotion

· BogoHalfPromotion

· TaxPolicy

· FlatVat

· ReducedDigitalVat

· Shippable

Выполнить:

1. Определить одну чётко выраженную ответственность (1–2 предложения).

2. Указать, какие изменения требований приведут к необходимости изменить этот класс.

3. Сделать вывод: соблюдён SRP или нарушен? Почему?


1.2. Аналализ OCP

Для тех же классов:

1. Объяснить, можно ли расширить этот класс (добавить функциональность) без изменения его исходного кода.

2. Если класс нарушает OCP, предложить способ его перепроектировать (кратко, без кода).


Часть 2 — Практика по OCP: расширение проекта без изменения существующих классов

Задание 2.1.

Добавить новый тип акции: Buy 3 — Pay for 2 (каждая третья единица бесплатна)

Требование:

· нельзя изменять ни один из существующих классов (Promotion, Product, PricePolicy и т.д.)

· разрешено только добавлять новые файлы и менять ShopDemo6.


Задание 2.2.

Добавить новую налоговую политику: ProgressiveVat

Правила:

· До 100 → +5%

· 100–500 → +10%

· Свыше 500 → +15%

Требования:

· Не менять существующие классы (OCP).

· Только добавить новый файл и обновить ShopDemo6.


Задание 2.3.

Перепроектировать систему доставки для строгого соблюдения OCP:

1. Создать интерфейс:

2. ShippingPolicy

3. Реализовать следующие политики:

o SimpleWeightShippingPolicy

o FreeOverThresholdShippingPolicy

o ExpressShippingPolicy

4. Изменить PhysicalProduct, чтобы:

o он не хранил логику доставки внутри себя

o принимал ShippingPolicy через конструктор

o делегировал расчёт стоимости доставки этой политике

5. Проверить, что теперь при добавлении новой доставки можно обходиться без изменения PhysicalProduct

