(ns clojure.sales
(:require [clojure.string :refer [split split-lines join]]))

(defn load_data[file]
(into (sorted-map)(map (fn [x] [(first x)(rest x) ]) (vec(map(fn [x] (split x #"\|")) (split-lines (slurp file)) )) ))
)

( def cust_db (load_data "cust.txt"))
( def prod_db (load_data "prod.txt"))
( def sales_db (load_data "sales.txt"))

(defn output_string
([arg]
  (map(fn[x]
   (str(str (name (x 0)) ":[" (join ", " (x 1)) "]"))
  ) arg))
)

(def namemap {})
(defn load_map [collection]
	  (def line (first collection))
	  (def cust_id (nth (first (rest line )) 0 ) )
    (def prod_id (nth (first (rest line )) 1 ) )
	  (def namemap (assoc namemap (first line)
     [(first (get cust_db cust_id)), (first (get prod_db prod_id)),(nth (first (rest line )) 2 ) ]))
  (if (empty? collection)
    (println " ")
    (load_map  (rest collection))
  )
)
(load_map sales_db)

(defn display_sales_table [collection]
  (if(= false (nil?(first collection)))
  ;; (do (println "hello" ))
  (do (def line (first collection))
    (def cust_id (nth (first (rest line )) 0 ) )
    (def prod_id (nth (first (rest line )) 1 ) )
    (println (first line) ":["
    (first (get cust_db cust_id)) ","
    (first (get prod_db prod_id)) ","
    (nth (first (rest line )) 2 )
     "]"
     )
	  (def namemap (assoc namemap (first line)
     [(first (get cust_db cust_id)), (first (get prod_db prod_id)),(nth (first (rest line )) 2 ) ]))
  )
  )
  (if (empty? collection)
    (println " ")
    (display_sales_table  (rest collection))
  )
)


(defn disp_cust_table[]
(def result (sort (vec(output_string cust_db))))
(doseq [item result]
   (println item))
)

(defn disp_prod_table[]
(def result (sort (vec(output_string prod_db))))
(doseq [item result]
   (println item))
)

(defn disp_sales_table[]
(display_sales_table  sales_db)
)

(defn prom[]
(print "Enter Customer Name:")
(println "?" )
)

(defn inner [collection item1]
(def item2 (first collection) )
(if (= false (nil? (first collection)) )
  (if(= (nth item1 1) (first item2))
  (do
  (def p1 (Float/parseFloat (first(rest item2))))
  (def p2 (Float/parseFloat (nth item1 2)  ))
  (def prod (* p1 p2))
  (def sum  (conj sum prod ))
  )
  )
)
  (if (empty? collection)
    (print-str " ")
    (inner  (rest collection) item1))
)


(defn outer [collection]
  (if (= false (nil? (first collection)) )
  (inner (vals prod_db) (first collection))
)
  (if (empty? collection)
    (print-str " ")
    (outer  (rest collection)))
)



(defn get_total_sales[]
(println "Enter Customer Name:")
(def custname (read-line))
(def valx (filter (fn [x]
  (= (first x) custname))
  (vals namemap)))
(def sum [])
(outer valx)
(println custname ":" (reduce + sum))
)

(defn loopcoll [collection]
(if (= false (nil? (first collection)) )
  (def sum  (conj sum (Integer/parseInt (nth (first collection) 2)  )))
)
  (if (empty? collection)
    (print-str "no more values to process")
    (loopcoll  (rest collection)))
)

(defn get_total_count[]
	(println "Enter product Name:")
	(def prodname (read-line))
	(def valy (filter (fn [x]
		(= (nth x 1) prodname))
		(vals namemap)))
	(def sum [])
  (loopcoll valy)
	(println prodname ":" (reduce + sum))
)

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
     (if(= "" choice) (def choice "0") )
     (def choice (read-string choice) )
     (println "--------------------------------------")
     (if (= false ( contains? {1 2 3 4 5 6} choice ))
       (println "Invalid choice!"))
     (if (= choice 1)(disp_cust_table))
     (if (= choice 2)(disp_prod_table))
     (if (= choice 3)(disp_sales_table))
     (if (= choice 4)(get_total_sales))
     (if (= choice 5)(get_total_count))
     (if(= choice 6)
       (println "Good Bye!")
       (menu_func)
     )
   )

(menu_func)
(println "--------------------------------------")
