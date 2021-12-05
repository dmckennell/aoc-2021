(ns aoc-2021.day05
  (:require [aoc-2021.core :as c]
            [clojure.string :as str]))

(defn parse-line [line]
  (->> line
       (re-seq #"\d+")
       (mapv #(Long/parseLong %))))

(defn parse-file [file]
  (->> (c/read-text-file file)
       (str/split-lines)
       (map parse-line)))

(defn straight-line? [[x1 y1 x2 y2]]
  (or (== x1 x2) (== y1 y2)))

(defn inclusive-range [start end]
  (if (<= start end)
    (range start (inc end))
    (range start (dec end) -1)))

(defn straight-line-points [[x1 y1 x2 y2]]
  (for [x (inclusive-range x1 x2)
        y (inclusive-range y1 y2)]
    [x y]))

(defn diagonal-line-points [[x1 y1 x2 y2]]
  (map vector (inclusive-range x1 x2) (inclusive-range y1 y2)))

(defn line-points [line]
  (if (straight-line? line)
    (straight-line-points line)
    (diagonal-line-points line)))

(defn solve [lines]
  (->> (mapcat line-points lines)
       frequencies
       (filter #(< 1 (val %)))
       count))

(defn part1 [file]
  (->> (parse-file file)
       (filter straight-line?)
       solve))

(defn part2 [file]
  (->> (parse-file file) solve))

(part1 "day05_demo")
;; => 5

(part1 "day05")
;; => 5084

(part2 "day05_demo")
;; => 12

(part2 "day05")
;; => 17882



