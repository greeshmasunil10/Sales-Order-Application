(ns clojure.sales
(:require [clojure.string :refer [split split-lines join]]))

(defn load_data[file]
  (into (sorted-map)(map (fn [x] [(first x)(rest x) ]) (vec(map(fn [x] (split x #"\|")) (split-lines (slurp file)) )) )))

( def cust_db (load_data "cust.txt"))
( def prod_db (load_data "prod.txt"))
( def sales_db (load_data "sales.txt"))

(defn output_string
  [arg]
    (map(fn[x]
      (str(str (name (x 0)) ":[" (join ", " (x 1)) "]"))
      ) arg))

(def namemap {})

(defn load_map [collection]
	  (def line (first collection))
	  (def cust_id (nth (first (rest line )) 0 ) )
    (def prod_id (nth (first (rest line )) 1 ) )
	  (def namemap (assoc namemap (first line)
     [(first (get cust_db cust_id)), (first (get prod_db prod_id)),(nth (first (rest line )) 2 ) ]))
    (if (empty? collection)
      (println " ")
    (load_map  (rest collection))))

(load_map sales_db)

(defn display_sales_table [collection]
  (if(= false (nil?(first collection)))
  (do (def line (first collection))
    (def cust_id (nth (first (rest line )) 0 ) )
    (def prod_id (nth (first (rest line )) 1 ) )
    (println (first line) ":["
    (first (get cust_db cust_id)) ","
    (first (get prod_db prod_id)) ","
    (nth (first (rest line )) 2 )
     "]")
	  (def namemap (assoc namemap (first line)
     [(first (get cust_db cust_id)), (first (get prod_db prod_id)),(nth (first (rest line )) 2 ) ]))))
  (if (empty? collection)
    (println " ")
    (display_sales_table  (rest collection))))

(defn disp_cust_table[]
  (def result (sort (vec(output_string cust_db))))
	(println "Customer Table")
  (println "--------------------------------------")
  (doseq [item result]
    (println item)))

(defn disp_prod_table[]
  (def result (sort (vec(output_string prod_db))))
	(println "Product Table")
  (println "--------------------------------------")
  (doseq [item result]
    (println item)))

(defn disp_sales_table[]
	(println "Sales Table")
  (println "--------------------------------------")
  (display_sales_table  sales_db))

(defn inner [collection item1]
  (def item2 (first collection) )
  (if (= false (nil? (first collection)) )
      (if(= (nth item1 1) (first item2))
        (do
          (def price (Float/parseFloat (first(rest item2))))
          (def item_count (Float/parseFloat (nth item1 2)  ))
          (def item_name (nth item1 1)  )
          (def prod (* price item_count))
          (println "Item:" item_name "Price:"price "Qty:" item_count)
          ;; (use 'clojure.pprint)
          ;; (print-table [{:Item item_name :Qty price :Price item_count}])
          (def sum  (conj sum prod )))))
  (if (empty? collection)
    (print-str " ")
    (inner  (rest collection) item1)))

(defn outer [collection]
  (if (= false (nil? (first collection)) )
    (inner (vals prod_db) (first collection)))
  (if (empty? collection)
    (print-str " ")
    (outer  (rest collection))))

(defn get_total_sales[]
  (println "Enter Customer Name:")
  (def custname (read-line))
  (def valx (filter (fn [x]
  (= (first x) custname))
  (vals namemap)))
  (def sum [])
  (println "--------------------------------------")
  (outer valx)
  (if(= 0 (reduce + sum))
      (println "Customer not found /or hasn't made any purchases.")
      (println "\nTotal purchase value for"custname ":" (reduce + sum))))

(defn item_count_list [collection]
  (if (= false (nil? (first collection)) )
    (def sum  (conj sum (Integer/parseInt (nth (first collection) 2)  ))))
  (if (empty? collection)
    (print-str "no more values to process")
    (item_count_list  (rest collection))))

(defn get_total_count[]
	(println "Enter product Name:")
	(def prodname (read-line))
	(def valy (filter (fn [x]
		(= (nth x 1) prodname))
		(vals namemap)))
	(def sum [])
  (item_count_list valy)
  (println "--------------------------------------")
  (if(= 0 (reduce + sum))
      (println "Product not found /or has not been sold.")
      (println "No of"prodname "sold:" (reduce + sum))))

(defn menu []
  (println "--------------------------------------")
   (println "\n*** Sales Menu ***")
   (println "--------------------------------------")
   (println "1. Display Customer Table")
   (println "2. Display Product Table")
   (println "3. Display Sales Table")
   (println "4. Total Sales for Customer")
   (println "5. Total Count for Product")
   (println "6. Exit")
   (println "Enter an option?"))

(defn menu_func[]
     (menu)
     (def choice (read-line) )
     (if(= "" choice) (def choice "-1") )
     (def choice (read-string choice) )
     (println "--------------------------------------")
     (if (= false ( contains? [1 2 3 4 5 6 7] choice ))
       (println "Invalid choice!"))
     (if (= choice 1)(disp_cust_table))
     (if (= choice 2)(disp_prod_table))
     (if (= choice 3)(disp_sales_table))
     (if (= choice 4)(get_total_sales))
     (if (= choice 5)(get_total_count))
     (if(= choice 6)
       (println "Good Bye!")
       (menu_func)))

(menu_func)
(println "--------------------------------------")
