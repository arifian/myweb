(ad/create-dt "datomic:mem://mbe")


{:1
 {:number 1,
  :title "Lorem Ipsum #1",
  :content ""},
 :2
 {:number 2,
  :title "Lorem Ipsum #2",
  :content ""},
 :3
 {:number 3,
  :title "asdf",
  :content "asdf"}}

#{[#uuid "58e74990-21ff-4a6e-a09e-0453c964761d" "Second best post in the world" "Ipsum Ingsun Dilema Labibade"]
  [#uuid "58e74990-a937-46a6-8f39-c0f8273b2de8" "Best post in the world" "Ipsum Ingsun Dilema"]}

#{{:id #uuid "58e74990-21ff-4a6e-a09e-0453c964761d"
       :title "Second best post in the world"
       :content "Ipsum Ingsun Dilema Labibade"}
  {:id #uuid "58e74990-a937-46a6-8f39-c0f8273b2de8"
     :title "Best post in the world"
     :content "Ipsum Ingsun Dilema"}}


({:number #uuid "58eb85fb-098f-4ec2-b4ec-a7ce16d402d4",
  :title "Ipsum Two",
  :content ""}
 {:number #uuid "58eb85fb-6913-4774-b19b-384a7d733cf2",
  :title "Ipsum One",
  :content ""}
 {:number #uuid "58eb85fb-125c-48b8-8ecd-d227de94d81f",
  :title "Ipsum Three",
  :content ""})
