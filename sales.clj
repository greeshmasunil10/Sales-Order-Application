(ns clojure.sales
(:require [clojure.string :refer [split split-lines join]]))
(defn menu []
   (println "*** Sales Menu ***")
   (println "------------------")
   (println "1. Display Customer Table")
   (println "2. Display Product Table")
   (println "3. Display Sales Table")
   (println "4. Total Sales for Customer")
   (println "5. Total Count for Product")
   (println "6. Exit")
   (println "Enter an option?"))
(menu)
(def choice (read-line))

(defn db[file]
(vec(map (fn [x] [(first x)(rest x) ]) (vec(map(fn [x] (split x #"\|")) (split-lines (slurp file)) )) ))
)

( def cust_db (db "cust.txt"))
( def prod_db (db "prod.txt"))
( def sales_db (db "sales.txt"))

(defn output_string[arg]
  (map(fn[x] 
   (str(str (name (x 0)) ":[" (join ", " (x 1)) "]"))
  ) arg))

(defn disp_cust_table[]
(def out (sort (vec(output_string cust_db))))
(doseq [item out]
   (println item))
(println "You have opted for choice 1"))

(defn disp_prod_table[]
(def out (sort (vec(output_string prod_db))))
(doseq [item out]
   (println item))
(println "You have opted for choice 2"))

(defn disp_sales_table[]
(def out (sort (vec(output_string sales_db))))
(doseq [item out]
   (println item))
(println "You have opted for choice 3"))

(defn get_total_sales[](println "You have opted for choice 4"))
(defn get_total_count[](println "You have opted for choice 5"))

(if(= choice "1")(disp_cust_table))
(if(= choice "2")(disp_prod_table))
(if(= choice "3")(disp_sales_table))
(if(= choice "4")(get_total_sales))
(if(= choice "5")(get_total_count))
(if(= choice "6")(println "Good Bye!"))

(println "the end")




