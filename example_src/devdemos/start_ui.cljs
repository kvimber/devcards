(ns devdemos.start-ui
  (:require
   [devcards.core]
   [devdemos.kevin]
   [devdemos.p5]
   [devdemos.new-card]
   [devdemos.defcard-api]
   [devdemos.om-next]
   [devdemos.reagent]
   [devdemos.source-code-display]
   [devdemos.two-zero]
   [devdemos.testing]
   [devdemos.errors]
   [devdemos.extentions]
   [devdemos.edn-render]
   [devdemos.css-opt-out]
   [devdemos.custom-cards]
   [devdemos.maintain_state]
   [devdemos.core]))

;; The main function here is actually used in a documentation
;; generator that I'm experimenting with. This is not needed
;; with a standard Devcards setup!!

(defn ^:export main []
  (devcards.core/start-devcard-ui!))
