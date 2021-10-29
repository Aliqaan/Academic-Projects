; ali kaan biber
; 2018400069
; compiling: yes
; complete: yes

#lang racket

;; given
(struct num (value grad)
    #:property prop:custom-write
    (lambda (num port write?)
        (fprintf port (if write? "(num ~s ~s)" "(num ~a ~a)")
            (num-value num) (num-grad num))))

;; given
 (define relu (lambda (x) (if (> (num-value x) 0) x (num 0.0 0.0))))
;; given
 (define mse (lambda (x y) (mul (sub x y) (sub x y))))


;; 3.1
(define (get-value num-list)
 (if (num? num-list) (num-value num-list) 
  (if (not (null? num-list)) (cons (num-value (eval (car num-list))) (get-value (cdr num-list))) (list) ) 
    ))
    

;; 3.2
(define (get-grad num-list)
  (if (num? num-list) (num-grad num-list) 
  (if (not (null? num-list)) (cons (num-grad (eval (car num-list))) (get-grad (cdr num-list))) (list) ) 
    ))

;4.1 add
(define add
  (lambda args (num (eval (cons '+ (get-value args))) (eval (cons '+ (get-grad args))))))

;4.2 mul 

(define mul
  (lambda args (num (eval (cons '* (get-value args))) (* (eval (cons '* (get-value args))) (eval (cons '+ (map / (get-grad args) (get-value args)) ))) )))

;4.3 sub
(define (sub num1 num2)
   (num (- (num-value num1) (num-value num2)) (- (num-grad num1) (num-grad num2)))
  )


;5.1  (create-hash names values var)

(define (create-hash names values var)
  (make-hash (lopmaker names values var))
     )

;;constructs a list of pairs to be used in create-hash function

(define (lopmaker names values var)
  (if (null? names) (list)
    (let ([name (car names)] [value (car values)] )
      (if (eq? name var) (cons (cons name (num value 1.0)) (lopmaker (cdr names) (cdr values) var )) (cons (cons name (num value 0.0)) (lopmaker (cdr names) (cdr values) var )))
      )))

;5.2  (parse hash expr )

(define (parse hash expr)
    (cond
      [(null? expr) (list)]
      [(list? expr) (cons (parse hash (car expr)) (parse hash (cdr expr)))]
      
      [(eq? expr '+) 'add ]
      [(eq? expr '*) 'mul ]
      [(eq? expr '-) 'sub ]
      [(eq? expr 'mse) 'mse ]
      [(eq? expr 'relu) 'relu ]
      
      [(number? expr) (num expr 0.0)]
      [else (hash-ref hash expr )]    
     ) )

;5.3 (grad names values var expr )

(define (grad names values var expr)
   (num-grad (eval (parse (create-hash names values var) expr)))
     )

;5.4 (partial-grad names values vars expr )

(define (partial-grad names values vars expr)
  (map (lambda (x) (if(member x vars)(grad names values x expr)0.0 ) ) names )
    )

;5.5 (gradient-descent names values vars lr expr )

(define (gradient-descent names values vars lr expr)
   (let ([subtract-values (map (lambda (num) (* lr num)) (partial-grad names values vars expr))])
   (map - values subtract-values)
   ))

;5.6 (optimize names values vars lr k expr )

(define (optimize names values vars lr k expr )
  (if (= k 1) (gradient-descent names values vars lr expr) (optimize names (gradient-descent names values vars lr expr) vars lr (- k 1) expr))
    )

