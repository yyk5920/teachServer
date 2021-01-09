# init register
lui $s0, 0
ori $s0, $s0, 1
lui $s1, 0
ori $s1, $s1, 2
lui $s2, 4097
ori $s2, $s2, 0
# initial operation completed
add $t0,$0,$0
ag:  slti $t2,$t0,10
beq $t2,$0,exit
add $s0,$s0,$s1
addi $t0,$t0,1
j ag
exit:
