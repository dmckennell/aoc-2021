(ns aoc-2021.day01
  (:require [clojure.java.io :as io]))

(defn parse-input [file]
  (map #(Long/parseLong %)
       (line-seq (io/reader (io/resource file)))))

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

;; some alt version to demo the mathematical idea here
(time (->> "day01.txt"
           parse-input
           (partition 3 1)
           (partition 2 1)
           (reduce (fn [acc window]
                     (let [[[x1 _ _] [_ _ y3]] window]
                       (if (> y3 x1)
                         (inc acc)
                         acc))) 0)))

;;demo 1
(solve-part1 "day01_demo")
;;real 1
(time (solve-part1 "day01"))
;;demo 1
(solve-part2 "day01_demo")
;;part 2
(time (solve-part2 "day01"))

;; online solutions beyond this point for future reference

(defn solve-part1-alt1 [l]
  (->> l
       (#(map < % (rest %)))
       (filter identity)
       count))

(time (solve-part1-alt1 (parse-input (str "day01" ".txt"))))

(defn solve-part2-alt1 [l]
  (->> l
       (#(map + % (rest %) (rest (rest %))))
       (#(map < % (rest %)))
       (filter identity)
       count))

(time (solve-part2-alt1 (parse-input (str "day01" ".txt"))))