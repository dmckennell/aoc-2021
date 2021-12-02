(ns aoc-2021.day02
  (:require [clojure.java.io :as io]))

(defn parse-commands [input]
  (for [[_ name amount] (re-seq #"(\w+)\s+(\d+)" input)]
    {:name (keyword name)
     :amount (Integer/parseInt amount)}))

(def starting-position
  {:aim 0 :horizontal 0 :depth 0})

(defn product-postiion [{:keys [horizontal depth]}]
  (* horizontal depth))

(defn part1 [cur-position {:keys [name amount]}]
  (case name
    :forward (update cur-position :horizontal + amount)
    :up      (update cur-position :depth - amount)
    :down    (update cur-position :depth + amount)))

(defn part2 [cur-position {:keys [name amount]}]
  (case name
    :forward (-> cur-position
                 (update :horizontal + amount)
                 (update :depth + (* (:aim cur-position) amount)))
    :up     (update cur-position :aim - amount)
    :down   (update cur-position :aim + amount)))

(defn solve [navigator file]
  (->> (slurp (io/resource file))
       parse-commands
       (reduce navigator starting-position)
       product-postiion))

;;demo part1
(solve part1 "day02_demo.txt")
;; => 150

;;real part1
(solve part1 "day02.txt")
;; => 2039256

;;demo part2
(solve part2 "day02_demo.txt")
;; => 900

;;real part2
(solve part2 "day02.txt")
;; => 1856459736
