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
(defn format-file [file]
  ; Split the file by lines, then split each line by components
  (vec (map (fn [s] (split s #"\|")) (split-lines (slurp file)))))
 (defn format-entry [entry]
  (str (name (entry 0)) ":[" (join ", " (entry 1)) "]"))


(defn to-database [file]
  ; This collapses the vector of line into a single map
  ; I love reduce
  (reduce (fn [db line] (assoc db (keyword (first line)) (vec (rest line)))) {} (sort (format-file file))))
  
(def customers-db ( to-database "cust.txt"))
(def products-db  (to-database "prod.txt"))
(def sales-db     (slurp"sales.txt"))
  

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

  
(defn get-string [db, formatter]
(join "\n" (map formatter (seq db))))

(def customers-text (get-string customers-db format-entry))
(def products-text (get-string products-db format-entry))


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




