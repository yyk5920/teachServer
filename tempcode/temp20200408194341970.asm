# init register
lui $s0, 0
ori $s0, $s0, 0
lui $s1, 0
ori $s1, $s1, 1
lui $s2, 4097
ori $s2, $s2, 0
lui $s0, 0
ori $s0, $s0, 0
lui $s1, 0
ori $s1, $s1, 1
lui $s2, 4097
ori $s2, $s2, 0
# initial operation completed
lui $s2, 0x1001
ori $s2, 0

addi $t0, $zero, 1
sw $t0, 4($s2)
addi $t0, $zero, 2
sw $t0, 8($s2)
addi $t0, $zero, 3
sw $t0, 12($s2)
addi $t0, $zero, 4
sw $t0, 16($s2)

addi $s0, $zero, 0
addi $s1, $zero, 1

ag:  slti $t2,$s0,10
beq $t2,$0,exit
sll $t3,$s0,2
add $t1,$s2,$t3
add $t3,$s0,$s1
sw $t3,0($t1)
addi $s0,$s0,1
j ag
exit:
