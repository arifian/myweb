(ns were-records)

(defrecord WereWolf [name title])

(WereWolf. "David" "London Tourist")
; => #were_records.WereWolf{:name "David", :title "London Tourist"}

(->WereWolf "Jacob" "Lead Shirt Discarder")
; => #were_records.WereWolf{:name "Jacob", :title "Lead Shirt Discarder"}

(map->WereWolf {:name "Lucian" :title "CEO of Melodrama"})
; => #were_records.WereWolf{:name "Lucian", :title "CEO of Melodrama"}

