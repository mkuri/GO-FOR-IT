(import 'java.util.Calendar)

(defn get-millis [a b c]
  (let [cal (Calendar/getInstance)]
    (.set cal a b c)
    (.getTimeInMillis cal)))

(defn what-time-is-today? [a b c n]
  (let [birth (get-millis a b c)
        now (System/currentTimeMillis)
        death (get-millis (+ a n 1) b c)
        secs (int (* (/ (- now birth)
                        (- death birth))
                     (* 24 60 60)))
        hour (int (/ secs (* 60 60)))
        minute (int (/ (- secs (* hour 60 60)) 60))
        second (int (mod (- secs (* hour 60 60)) 60))]
    (println (str hour ":" minute ":" second))))

(defn argv [n] (Integer/parseInt (nth *command-line-args* n)))

(let [a (argv 0) b (argv 1) c (argv 2) n (argv 3)]
  (what-time-is-today? a b c n))