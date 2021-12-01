(ns aoc-2021.day01
  (:require [aoc-2021.core :as c]))

(defn parse-input [file]
  (map #(Long/parseLong %)
       (line-seq (c/input-reader file))))

(defn compare-and-increment [count window]
  (let [[val1 val2 & _] window]
    (if (> val2 val1)
      (inc count)
      count)))


(defn solve-part1 [filename]
  (->> (str filename ".txt")
       parse-input
       (partition 2 1)
       (reduce compare-and-increment 0)))

(defn solve-part2 [filename]
  (->> (str filename ".txt")
       parse-input
       (partition 3 1)
       (map #(apply + %))
       (partition 2 1)
       (reduce compare-and-increment 0)))

;;demo 1
(solve-part1 "day01_demo")
;;real 1
(solve-part1 "day01")
;;demo 1
(solve-part2 "day01_demo")
;;part 2
(solve-part2 "day01")