# init register
lui $a0, 0
ori $a0, $a0, 10
lui $a1, 0
ori $a1, $a1, 20
lui $a2, 0
ori $a2, $a2, 30
lui $a3, 0
ori $a3, $a3, 40
# initial operation completed
add $t0,$a0,$a1
add $t0,$t0,$a2
add $t0,$t0,$a3

addi $t1,$a1,5
add $t1,$t1,$a0

add $t2,$a0,$a0
sub $t2,$t2,$a1

addi $t3,$a1,-5
add $t3,$t3,$a0

mult $a0,$a1
mfhi $t4
mflo $t5

div $a0,$a1
mfhi $t7
mflo $t6
