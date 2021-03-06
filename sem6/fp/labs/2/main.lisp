(defun subsets(s) 
	(if s (mapcan (lambda (x) (list (cons (car s) x) x))
		(subsets(cdr s)))
			'(())))
			
(print "List 1")
(print (subsets '(1 2 3)))
(print "List 2")
(print (subsets '(1)))
(print "List 3")
(print (subsets '(1 2)))
(print "List 4")
(print (subsets '(1 2 3 4)))
(print "List 5")
(print (subsets '(1 2 3 4 5)))
(print "List 6")
(print (subsets '()))
