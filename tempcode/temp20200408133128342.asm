# init memory
lui $t0, 4097
ori $t0, $t0, 4
lui $t1, 0
ori $t1, $t1, 1
sw $t1, 0($t0)
lui $t0, 4097
ori $t0, $t0, 8
lui $t1, 0
ori $t1, $t1, 2
sw $t1, 0($t0)
lui $t0, 4097
ori $t0, $t0, 12
lui $t1, 0
ori $t1, $t1, 3
sw $t1, 0($t0)
lui $t0, 4097
ori $t0, $t0, 16
lui $t1, 0
ori $t1, $t1, 4
sw $t1, 0($t0)
lui $t0, 4097
ori $t0, $t0, 36
lui $t1, 0
ori $t1, $t1, 1
sw $t1, 0($t0)
lui $t0, 4097
ori $t0, $t0, 40
lui $t1, 0
ori $t1, $t1, 2
sw $t1, 0($t0)
lui $t0, 4097
ori $t0, $t0, 44
lui $t1, 0
ori $t1, $t1, 3
sw $t1, 0($t0)
lui $t0, 4097
ori $t0, $t0, 48
lui $t1, 0
ori $t1, $t1, 4
sw $t1, 0($t0)
addi $t0, $zero, 0
addi $t1, $zero, 0
# init register
lui $s1, 0
ori $s1, $s1, 1
lui $s2, 0
ori $s2, $s2, 2
lui $s3, 0
ori $s3, $s3, 3
lui $s4, 0
ori $s4, $s4, 4
lui $s6, 4097
ori $s6, $s6, 0
lui $s7, 4097
ori $s7, $s7, 32
# initial operation completed
#s0->t4
lw $t0,16($s7)
add $t4,$s1,$s2
add $t4,$t4,$t0

#s0->t5
lw $t0,4($s7)
lw $t1,8($s6)
add $t5,$t1,$t0
add $t5,$t5,$s1

#s0->t6
sll $t0,$s1,2
add $t0,$t0,$s7
lw $t1,0($t0)
add $t1,$t1,$s3
sll $t1,$t1,2
add $t1,$t1,$s6
lw $t6,0($t1)

#s0->t7
sll $t0,$s4,2
add $t0,$t0,$s7
lw $t1,0($t0)
sll $t1,$t1,2
add $t1,$t1,$s6
lw $t2,0($t1)
sub $t7,$s1,$t2
