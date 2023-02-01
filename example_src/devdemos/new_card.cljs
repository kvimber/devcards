(ns devdemos.new-card
  (:require
   [devcards.core]
   [devcards.util.utils :refer [html-env?]]
   [clojure.string :as string]
   [clojure.set :refer [difference union]]
   [sablono.core :as sab :include-macros true]

   [om.core :as om :include-macros true]
   [om.dom :as dom :include-macros true]
   [cljs.core.async :refer [timeout]]
   [goog.labs.userAgent.device :as device]
   [cljs.test :as t :include-macros true]
   )
  (:require-macros
   [cljs.core.async.macros :refer [go]]
   [devcards.core :as dc :refer [defcard defcard-doc deftest]]
   )
  )

(defcard
  "# New Card Workflow!
   - [ ] update namespace
   - [ ] put your card in start_ui.cljs"
  )
