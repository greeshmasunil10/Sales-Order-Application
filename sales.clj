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
([arg1 arg2 arg3]
	str(
    (map(fn[x] 
   (str(str (name (x 0)) ":[")) 
        ) arg3)

		(map(fn[x] 
   (str( (join ", " (x 1)) "]"))
        ) arg1)

		(map(fn[x] 
   (str( (join ", " (x 1)) "]"))
        ) arg2)
  ) 
)
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
(println "You have opted for choice 3")
)

(defn prom[] 
(print "Enter Customer Name:")
(println "?" )
)
(defn get_total_sales[]
	(println "Enter Customer Name:")
	(def custname (read-line))
	(def valx (filter (fn [x]
		(= (first x) custname))
		(vals namemap)))
	(def sum [])
	(doseq [item1 valx] 
	 (doseq [item2 (vals prod_db)]
		(if(= (nth item1 1) (first item2))
		(def sum  (conj sum (Float/parseFloat (first(rest item2)))))
		)
	 )
	)
	(println custname ":" (reduce + sum)) 

)




(defn get_total_count[](println "You have opted for choice 5"))

(if(= choice "1")(disp_cust_table))
(if(= choice "2")(disp_prod_table))
(if(= choice "3")(disp_sales_table))
(if(= choice "4")(get_total_sales))
(if(= choice "5")(get_total_count))
(if(= choice "6")(println "Good Bye!"))

(println "The End")


