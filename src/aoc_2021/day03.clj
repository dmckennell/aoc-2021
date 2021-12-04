(ns aoc-2021.day03
  (:require [aoc-2021.core :as c]))

(defn input [filename] (c/read-text-file filename))

;; rows parsed to int with radix 2
(defn parse [s]
  (->> s (re-seq #"[01]+") (map #(Integer/parseInt % 2))))

(parse (input "day03_demo"))

(defn most-common-bit [xs i]
  (let [f (->> xs (map #(bit-test % i)) frequencies)]
    (max-key #(get f % 0) false true)))

(defn part-1* [width xs]
  (->> (range width)
       (reduce (fn [[gamma epsilon] i]
                 (if (most-common-bit xs i)
                   [(bit-set gamma i) epsilon]
                   [gamma (bit-set epsilon i)]))
               [0 0])
       (apply *)))

(defn rating [width criteria xs]
  (loop [i (dec width) xs xs]
    (if (= 1 (count xs))
      (first xs)
      (let [target (criteria xs i)]
        (recur (dec i) (filter #(= target (bit-test % i)) xs))))))

(defn part-2* [width xs]
  (* (rating width most-common-bit xs)
     (rating width (complement most-common-bit) xs)))

(part-1* 5 (parse (input "day03_demo")))
(part-1* 12 (parse (input "day03")))

(part-2* 5 (parse (input "day03_demo")))
(part-2* 12 (parse (input "day03")))