(use 'clojure.string)
(use 'clojure.contrib.duck-streams)

(defn get-tune [x]
  (let [tune (Integer/parseInt (first x))]
    (if (= 2 (count x)) tune
        (cond (= (nth x 2) "s") (+ tune 0.5)
              (= (nth x 2) "x") (+ tune 1.0)
              (= (nth x 2) "b") (- tune 0.5)
              (= (nth x 2) "d") (- tune 1.0)))))

(defn get-length [x]
  (let [data (second x)]
    (if (.endsWith data ".") (* (/ 4 (Integer/parseInt
                                      (apply str (take (dec (count data)) data))))
                                1.5)
        (/ 4 (Integer/parseInt data)))))

(defn eval-diff [x]
  (let [a (first x) b (second x)]
    (if (or (empty? (first a)) (empty? (first b))) 0
        (* (Math/abs (- (get-tune a) (get-tune b)))
           (get-length a)))))

(defn get-vec [filename]
  (map #(split % #"\:") (split (apply str (read-lines filename)) #"\,")))

(defn get-feature-quantity [filename]
  (reduce + (map eval-diff (partition 2 1 (get-vec filename)))))

(let [filename (first *command-line-args*)]
  (get-feature-quantity filename))